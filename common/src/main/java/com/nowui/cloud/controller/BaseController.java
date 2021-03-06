package com.nowui.cloud.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netflix.client.ClientException;
import com.nowui.cloud.constant.Constant;
import com.nowui.cloud.entity.BaseEntity;
import com.nowui.cloud.exception.BaseException;
import com.nowui.cloud.util.Util;
import com.nowui.cloud.util.ValidateUtil;
import org.apache.ibatis.binding.BindingException;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import java.net.SocketTimeoutException;
import java.sql.SQLException;
import java.util.*;

/**
 * @author ZhongYongQiang
 */
@Transactional
public class BaseController {

    private String[] validateResponseColumnList = new String[]{};

    
    protected HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public <T> T getEntry(Class<T> clazz) {
        if (getRequest().getAttribute(Constant.REQUEST_BODY) == null) {
            getRequest().setAttribute(Constant.REQUEST_BODY, Util.readData(getRequest()));
        }
        return JSONObject.parseObject(Util.readData(getRequest()), clazz);
    }

    private JSONObject checkMap(Object data) {
        JSONObject result = (JSONObject) JSON.toJSON(data);

        Iterator iterator = result.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
            String key = entry.getKey();

            Boolean isNotExit = true;
            for (String column : validateResponseColumnList) {
                if (column.equals(key)) {
                    isNotExit = false;
                }
            }

            if (isNotExit) {
                iterator.remove();
            } else {

            }
        }

        return result;
    }

    private Object checkFirstResponse(Object data) {
        if (data instanceof BaseEntity) {
            return checkMap(data);
        } else if (data instanceof List) {
            List list = new ArrayList();
            for (Object item : (List) data) {
                if (item instanceof BaseEntity) {
                    list.add(checkMap(item));
                } else if (item instanceof Map) {
                    list.add(checkMap(item));
                }
            }

            return list;
        } else if (data instanceof Boolean) {
            return data;
        }

        return null;
    }

    @ResponseBody
    @ExceptionHandler(value = {BaseException.class, RuntimeException.class})
    public Map<String, Object> handleRuntimeException(RuntimeException e) {
        e.printStackTrace();

        String prefix = "com.netflix.client.ClientException: ";
        String message = e.getMessage();

        Map<String, Object> map = new HashMap<String, Object>(Constant.DEFAULT_LOAD_FACTOR);

        if (message.startsWith(prefix)) {
            map.put(Constant.CODE, 500);
            map.put(Constant.MESSAGE, "网络出现错误");
        } else {
            map.put(Constant.CODE, 400);
            map.put(Constant.MESSAGE, e.getMessage());
        }

        return map;
    }

    @ResponseBody
    @ExceptionHandler(value = {SQLException.class, MyBatisSystemException.class, RedisConnectionFailureException.class, IllegalStateException.class, BindingException.class, ClientException.class, SocketTimeoutException.class})
    public Map<String, Object> handleException(Exception e) {
        e.printStackTrace();

        Map<String, Object> map = new HashMap<String, Object>(Constant.DEFAULT_LOAD_FACTOR);
        map.put(Constant.CODE, 500);
        map.put(Constant.MESSAGE, "网络出现错误");
        return map;
    }

    public void validateRequest(Class<? extends BaseEntity> clazz, String... columns) {
        BaseEntity entity = getEntry(clazz);

        System.out.println(entity.toJSONString());

        validateRequest(entity, columns);
    }

    public void validateRequest(BaseEntity entity, String... columns) {
        for (String column : columns) {
            Set<? extends ConstraintViolation<? extends BaseEntity>> constraintViolations = ValidateUtil.getValidator().validateValue(entity.getClass(), column, entity.get(column));

            Iterator<ConstraintViolation<BaseEntity>> iterator = (Iterator<ConstraintViolation<BaseEntity>>) constraintViolations.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation<BaseEntity> constraintViolation = iterator.next();
                throw new BaseException(constraintViolation.getMessage());
            }
        }
    }

    public void validateResponse(String... columns) {
        validateResponseColumnList = columns;
    }

    protected Map<String, Object> renderJson(Object data) {
        data = checkFirstResponse(data);

        Map<String, Object> map = new HashMap<String, Object>(Constant.DEFAULT_LOAD_FACTOR);
        map.put(Constant.CODE, 200);
        map.put(Constant.DATA, data);
        return map;
    }

    protected Map<String, Object> renderJson(int total, Object data) {
        data = checkFirstResponse(data);

        Map<String, Object> dataMap = new HashMap<String, Object>(Constant.DEFAULT_LOAD_FACTOR);
        dataMap.put(Constant.TOTAL, total);
        dataMap.put(Constant.LIST, data);

        Map<String, Object> map = new HashMap<String, Object>(Constant.DEFAULT_LOAD_FACTOR);
        map.put(Constant.CODE, 200);
        map.put(Constant.DATA, dataMap);
        return map;
    }

}