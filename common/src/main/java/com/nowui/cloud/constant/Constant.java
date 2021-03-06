package com.nowui.cloud.constant;

/**
 * @author ZhongYongQiang
 */
public class Constant {

    public static String PRIVATE_KEY;

    public static final String CODE = "code";

    public static final String TOTAL = "total";

    public static final String DATA = "data";

    public static final String LIST = "list";

    public static final String MESSAGE = "message";
    
    public static final String CHILDREN = "children";

    public static final String PUBLISH = "publish";

    public static final int DEFAULT_LOAD_FACTOR = 1;
    
    public static final String THUMBNAIL = "thumbnail";
    
    public static final String ORIGINAL = "original";
    
    public static final String UPLOAD = "upload";

    public static final String REQUEST_BODY = "requestBody";
    
    public static final String UPLOAD_IMAGE_TYPES = ".jpg.jpeg.gif.png.bmp.JPG.JPEG.GIF.PNG.BMP";
    
    public static final String EXPIRE_TIME = "expireTime";
    
    public static final String TOKEN = "token";
    
    public static final String PLATFORM = "platform";
    
    public static final String VERSION = "version";
    
    static {
        PRIVATE_KEY = System.getenv("PRIVATE_KEY");

        if (PRIVATE_KEY == null) {
            throw new RuntimeException("私密不能为空");
        }
    }

}
