package com.coldlake.app.payment.utils;


import java.util.Random;
import java.util.UUID;

public class RandStrUtils {
    /**
     * 随机获取6位数字和字母的组合字符串
     *
     * @param length
     * @return
     */
    public static String getRandomCharAndNum(Integer length) {
        String str = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            boolean b = random.nextBoolean();
            // 字符串
            if (b) {
                // int choice = random.nextBoolean() ? 65 : 97; 取得65大写字母还是97小写字母
                // 取得小写字母
                str += (char) (97 + random.nextInt(26));
            }
            // 数字
            else {
                str += String.valueOf(random.nextInt(10));
            }
        }
        return str;
    }

    /**
     * 获取指定长度的随机数字字符串
     *
     * @param length
     * @return
     */
    public static String getRandomNumString(int length) {
        // 定义一个字符串（ 0-9）即10位；
        final String str = "0123456789";
        // 由Random生成随机数
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        // 长度为几就循环几次
        for (int i = 0; i < length; ++i) {
            // 产生0-61的数字
            int number = random.nextInt(str.length());
            // 将产生的数字通过length次承载到sb中
            sb.append(str.charAt(number));
        }
        // 将承载的字符转换成字符串
        return sb.toString();
    }

    /**
     * 生成订单号
     *
     * @return
     */
    public static String generateOrderId(String uid) {
        String uidStr = String.valueOf(Long.parseLong(HashUtils.md5(uid).substring(0, 14), 16));
        return TimeUtils.getCurrentTimeMils() + uidStr.substring(0, 6) + getRandomNumString(3);
    }

    /**
     * 生成随机id
     *
     * @return
     */
    public static String getRandId() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return "rand-" + TimeUtils.getCurrentTimeMils() + "-" + uuid.substring(0, 5);
    }

    /**
     * 生成任务id
     *
     * @return
     */
    public static String generateTaskId(String prefix) {
        return prefix + TimeUtils.timeFormatWithUnixTimestampAndFormat(TimeUtils.getCurrentTimeStamp(), "yyyyMMddHHmmss") + UUID.randomUUID()
                .toString().replace("-", "").toLowerCase().substring(0, 18);
    }

}