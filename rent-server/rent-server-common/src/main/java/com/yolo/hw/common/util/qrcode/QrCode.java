package com.yolo.hw.common.util.qrcode;

import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author:shenxi
 * @Des:二维码生成
 * @Date:2018/5/25
 */
public class QrCode {

    /**
     * 生成条形码图片
     * @param content 条形内容
     * @param w 宽度
     * @param h 高度
     * @return
     * @throws Exception
     */
    public static String creatCODE128(String content, int w, int h ) throws Exception {

        int codeWidth = 3 + // start guard
                (7 * 6) + // left bars
                5 + // middle guard
                (7 * 6) + // right bars
                3; // end guard
        codeWidth = Math.max(codeWidth, w);

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        //参数调节
        Map hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 0);
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.CODE_128, codeWidth, h, hints);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        //BASE64Decoder decoder = new BASE64Decoder();
        //Base64解码
        //byte[] logoBytes = decoder.decodeBuffer(logo);

        MatrixToImageWriter.writeToStream(bitMatrix, "png", stream,null);
        byte[] bytes = stream.toByteArray();
        BASE64Encoder encoder = new BASE64Encoder();
        String base64Str ="data:image/png;base64,"+encoder.encodeBuffer(bytes).trim();
        return base64Str;
    }


    /**
     * 生成二维码图片
     * @param content 二维码内容
     * @param w 宽度
     * @param h 高度
     * @param logo 中心图片数据，图片的字节码
     * @return
     * @throws Exception
     */
    public static byte[] createImage(String content, int w, int h, byte[] logo) throws Exception {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        //参数调节
        Map hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 0);
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, w, h, hints);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        //BASE64Decoder decoder = new BASE64Decoder();
        //Base64解码
        //byte[] logoBytes = decoder.decodeBuffer(logo);

        MatrixToImageWriter.writeToStream(bitMatrix, "png", stream,logo);
        byte[] bytes = stream.toByteArray();
        return bytes;
    }
    /**
     * 生成二维码图片
     * @param content 二维码内容
     * @param w 宽度
     * @param h 高度
     * @param logo 中心图片数据，图片的字节码
     * @return
     * @throws Exception
     */
    public static String createImge(String content, int w, int h, byte[] logo) throws Exception {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        //参数调节
        Map hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 0);
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, w, h, hints);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        //BASE64Decoder decoder = new BASE64Decoder();
        //Base64解码
        //byte[] logoBytes = decoder.decodeBuffer(logo);

        MatrixToImageWriter.writeToStream(bitMatrix, "png", stream,logo);
        byte[] bytes = stream.toByteArray();
        BASE64Encoder encoder = new BASE64Encoder();
        String base64Str ="data:image/png;base64,"+encoder.encodeBuffer(bytes).trim();
        return base64Str;
    }

    public static String readImge(String imge) throws Exception {
        if (imge == null) //图像数据为空
            return null;
        String strImge=imge.substring(imge.indexOf(",")+1);

        BASE64Decoder decoder = new BASE64Decoder();
        //Base64解码
        byte[] b = decoder.decodeBuffer(strImge);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(b);

        BufferedImage image = ImageIO.read(inputStream);

        LuminanceSource source = new BufferedImageLuminanceSource(image);
        Binarizer binarizer = new HybridBinarizer(source);
        BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
        Map hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        MultiFormatReader formatReader = new MultiFormatReader();
        Result result = formatReader.decode(binaryBitmap, hints);
//        System.out.println("解析结果:" + result.toString());
//        System.out.println("二维码格式:" + result.getBarcodeFormat());
//        System.out.println("二维码文本内容:" + result.getText());
        return result.toString();
    }

    /**
     * 生成二维码图片
     * @param content 二维码内容
     * @param w 宽度
     * @param h 高度
     * @param logo 中心图片数据，图片的字节码
     * @return
     * @throws Exception
     */
    public static String createImgeLog(String content, int w, int h) throws Exception {
        byte[] logo = getLog();
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        //参数调节
        Map hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 0);
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, w, h, hints);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        //BASE64Decoder decoder = new BASE64Decoder();
        //Base64解码
        //byte[] logoBytes = decoder.decodeBuffer(logo);

        MatrixToImageWriter.writeToStream(bitMatrix, "png", stream,logo);
        byte[] bytes = stream.toByteArray();
        BASE64Encoder encoder = new BASE64Encoder();
        String base64Str ="data:image/png;base64,"+encoder.encodeBuffer(bytes).trim();
        return base64Str;
    }
    public static byte[] getLog() {
        String logo="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";
        BASE64Decoder decoder = new BASE64Decoder();
        //Base64解码
        byte[] logoBytes = new byte[0];
        try {
            logoBytes = decoder.decodeBuffer(logo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logoBytes;
    }

    public static void main(String[] args) {
        try {
            String logo="iVBORw0KGgoAAAANSUhEUgAAAGQAAABkCAYAAABw4pVUAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyhpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNi1jMTM4IDc5LjE1OTgyNCwgMjAxNi8wOS8xNC0wMTowOTowMSAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTcgKE1hY2ludG9zaCkiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6NzAxNzhCMDIxRjc1MTFFOEE2MENBQjk4MEI4QjlFNTgiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6NzAxNzhCMDMxRjc1MTFFOEE2MENBQjk4MEI4QjlFNTgiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDo3MDE3OEIwMDFGNzUxMUU4QTYwQ0FCOTgwQjhCOUU1OCIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDo3MDE3OEIwMTFGNzUxMUU4QTYwQ0FCOTgwQjhCOUU1OCIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/PoCh9AgAAAuZSURBVHja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5dQoJuMUy+OBZD+Y104OtEyODhPbMsqssC6Wp8eB01JAnltrkJuZhiqnQeCkS+8IJsLL8jCN5YnbqLsce53Q0Bs512agHIKRNcWI0voXlvatIMOafjk3n6FB/XUoD0NgWNipYiKcLI20QWg1RJiQMNIJERPzAkoJyg8hMA9MLWhgaSphaZQ9bcPNOpHWWsT7fyBMez6uxfBqGDgUQL5ZJzfw+negiWt+qIvj9/HdY2Eps07kRB2TbYyQ5RDY/nwxJP/89mYI+JpoTd/7AiEqgh7UhxtMBk6opldoFmtYJzMZD4HNO2nOGO2HkcW+24tCk6Y6WQPcCoGtQ0iuoFxCaQMV4/8CDABwc+qRP7N22QAAAABJRU5ErkJggg==/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNi1jMTM4IDc5LjE1OTgyNCwgMjAxNi8wOS8xNC0wMTowOTowMSAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTcgKE1hY2ludG9zaCkiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6MURCOUYyNDAxREU0MTFFOEJENDBEMUFENjkzNzI0QTUiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6MURFNEU0NUExREU0MTFFOEJENDBEMUFENjkzNzI0QTUiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDoxREI5RjIzRTFERTQxMUU4QkQ0MEQxQUQ2OTM3MjRBNSIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDoxREI5RjIzRjFERTQxMUU4QkQ0MEQxQUQ2OTM3MjRBNSIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/PiYrl5EAABeASURBVHja7F0JuFVVFV7vgQhIiKSp5QAKGioapYIjhGjiQE4MIhVpoVmoaQWaOGWJU5ghJllhYAoNmopIGRCkSI4gDqgpmpiIJhiDCTzav3vdfD7fveesffY+w73r/7714ec7555hn7X32mv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";
            BASE64Decoder decoder = new BASE64Decoder();
            //Base64解码
            byte[] logoBytes = decoder.decodeBuffer(logo);
            String str = QrCode.createImge("1221412411", 400, 400, logoBytes);
            System.out.println(str);
            String content=QrCode.readImge(str);
            System.out.println(content);

//            String str1 =QrCode.createImge("11641720082131",220,50);
//            String content1=QrCode.readImge(str1);
            //System.out.println(content1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
