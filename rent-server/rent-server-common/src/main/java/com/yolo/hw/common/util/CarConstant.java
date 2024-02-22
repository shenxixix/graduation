package com.yolo.hw.common.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CarConstant {
    public static Map<Integer, String> gasNoMap = new HashMap<>();

    static {
        // 燃油标号  1 89#, 2 92#, 3 95#, 4 98#, 5 0#, 6 电, 7 5#, 8 10#, 9 -10#, 10 -20#, 11 -35#, 12 -50#
        gasNoMap.put(1, "89# 汽油");
        gasNoMap.put(2, "92# 汽油");
        gasNoMap.put(3, "95# 汽油");
        gasNoMap.put(4, "98# 汽油");
        gasNoMap.put(5, "0# 柴油");
        gasNoMap.put(7, "5# 柴油");
        gasNoMap.put(8, "10# 柴油");
        gasNoMap.put(9, "-10# 柴油");
        gasNoMap.put(10, "-20# 柴油");
        gasNoMap.put(11, "-35# 柴油");
        gasNoMap.put(12, "-50# 柴油");
    }

    public static Map<Integer, String> carBodyMap = new HashMap<>();

    static {
        // 车辆类型 1、轿车 2、越野车(SUV) 3、商务车(MPV)  4、中型客车  5、大型客车  6、货车 7、皮卡 8、面包车 99、其他
        carBodyMap.put(1, "轿车");
        carBodyMap.put(2, "越野车(SUV)");
        carBodyMap.put(3, "商务车(MPV)");
        carBodyMap.put(4, "中型客车");
        carBodyMap.put(5, "大型客车");
        carBodyMap.put(6, "货车");
        carBodyMap.put(7, "皮卡");
        carBodyMap.put(8, "面包车");
        carBodyMap.put(9, "洒水车");
        carBodyMap.put(10, "垃圾车");
        carBodyMap.put(11, "吸污车");
        carBodyMap.put(12, "吸粪车");
        carBodyMap.put(13, "清洗车");
        carBodyMap.put(14, "清扫车");
        carBodyMap.put(15, "吸尘车");
        carBodyMap.put(16, "清雪车");
        carBodyMap.put(17, "运渣车");
        carBodyMap.put(18, "高空作业车");
        carBodyMap.put(99, "其他");
    }

    public static Map<Integer, String> carNatureMap = new HashMap<>();

    static {
        // 使用性质 1 非营运 2 营运
        carNatureMap.put(1, "非营运");
        carNatureMap.put(2, "营运");
    }

    public static Map<Integer, String> carSeatMap = new HashMap<>();

    static {
        // 座位数  1 2座,2 4座,3 5座,4 6座,5 7座,6 7座以上
        carSeatMap.put(1, "2座");
        carSeatMap.put(2, "4座");
        carSeatMap.put(3, "5座");
        carSeatMap.put(4, "6座");
        carSeatMap.put(5, "7座");
        carSeatMap.put(6, "7座以上");
    }

    public static Map<Integer, String> carOutputMap = new HashMap<>();

    static {
        // 排量区间 1 1.6L及以下,2 1.7L-2.0L,3 2.1L-2.5L,4 2.6L-3.0L,5 3.1L-4.0L,6 4.1L及以上
        carOutputMap.put(1, "1.6L及以下");
        carOutputMap.put(2, "1.7L-2.0L");
        carOutputMap.put(3, "2.1L-2.5L");
        carOutputMap.put(4, "2.6L-3.0L");
        carOutputMap.put(5, "3.1L-4.0L");
        carOutputMap.put(6, "4.1L及以上");
    }

    public static Map<Integer, String> energyMap = new HashMap<>();

    static {
        // 燃料形式 1 汽油 2 柴油 3 纯电动 4 插电式混动(油电混合) 5 增程式(天然气)
        energyMap.put(1, "汽油");
        energyMap.put(2, "柴油");
        energyMap.put(3, "纯电动");
        energyMap.put(4, "插电式混动");
        energyMap.put(5, "增程式");
        energyMap.put(6, "其他");
    }

    public static Map<Integer, String> carEmissionMap = new HashMap<>();

    static {
        // 排放标准 1 国六 2 国五 3 国四 4 国三 5 国二 6 国一
        carEmissionMap.put(1, "国六");
        carEmissionMap.put(2, "国五");
        carEmissionMap.put(3, "国四");
        carEmissionMap.put(4, "国三");
        carEmissionMap.put(5, "国二");
        carEmissionMap.put(6, "国一");
    }

    public static Map<Integer, String> inOutFlagMap = new HashMap<>();

    static {
        // 接/拆预警  0 正常 1 屏蔽
        inOutFlagMap.put(0, "正常");
        inOutFlagMap.put(1, "屏蔽");
    }

    public static Map<Integer, String> payTypeMap = new HashMap<>();

    static {
        // 支付方式 1 油卡 2 现金 3 支付宝 4 微信 5 银行卡
        payTypeMap.put(1, "油卡");
        payTypeMap.put(2, "现金");
        payTypeMap.put(3, "支付宝");
        payTypeMap.put(4, "微信");
        payTypeMap.put(5, "银行卡");
    }

    public static Map<Integer, String> otherTypeMap = new HashMap<>();
    static {
        //费用类型 1 停车费 2 洗车美容 3 过路费 4 违章罚款 5 车载设备 99 其他
        otherTypeMap.put(1, "停车费");
        otherTypeMap.put(2, "洗车美容");
        otherTypeMap.put(3, "过路费");
        otherTypeMap.put(4, "违章罚款");
        otherTypeMap.put(5, "车载设备");
        otherTypeMap.put(99, "其他");
    }

    // 告警类型
    public final static int ALARM_0001 = 0X0001;  //点火告警
    public final static int ALARM_0103 = 0x0103;  //拔出告警
    public final static int ALARM_0105 = 0x0105;  //低电压告警
    public final static int ALARM_0114 = 0x0114;  //碰撞告警

    public static Map<Long, String> alarmMap = new HashMap<>(); // 告警文本MAP
    static {
        alarmMap.put(0x0001L, "车辆启动");
        alarmMap.put(0x0002L, "车辆熄火");
        alarmMap.put(0x0111L, "急加速");
        alarmMap.put(0x0112L, "急减速");
        alarmMap.put(0x0113L, "急转弯");
        alarmMap.put(0x0108L, "疲劳驾驶");
        alarmMap.put(0x0107L, "超速驾驶");
        alarmMap.put(0x0104L, "终端已接入");
        alarmMap.put(0x0103L, "终端被拆卸");
        alarmMap.put(0x9999L, "车辆故障");
        alarmMap.put(0x0105L, "电瓶电压低");
        alarmMap.put(0x9995L, "出禁出围栏");
        alarmMap.put(0x9994L, "进禁入围栏");
        alarmMap.put(0x9993L, "无任务外出");
        alarmMap.put(0x9992L, "禁停区停留");
        alarmMap.put(0x0114L, "车辆碰撞");
        alarmMap.put(0x0106L, "怠速时间过长");
        alarmMap.put(0x8800L, "DSM-疲劳驾驶");
        alarmMap.put(0x8801L, "DSM-分神");
        alarmMap.put(0x8802L, "DSM-打电话");
        alarmMap.put(0x8803L, "DSM-抽烟");
        alarmMap.put(0x8804L, "ADAS-前车碰撞");
        alarmMap.put(0x8805L, "ADAS-行人碰撞");
        alarmMap.put(0x8806L, "ADAS-车道偏离");
        alarmMap.put(0x8807L, "ADAS-车距过近");
    }

    public static Map<Long, String> alarmDetailMap = new HashMap<>(); // 告警文本MAP
    static {
//        alarmDetailMap.put(0x0001L, "%s");
        alarmDetailMap.put(0x0002L, "本次行驶%s公里");
        alarmDetailMap.put(0x0111L, "易增加油耗对发动机、轮胎、悬挂等部件有损害");
        alarmDetailMap.put(0x0112L, "易造成追尾事故对轮胎、悬挂、刹车等部件有损害");
        alarmDetailMap.put(0x0113L, "雨雪天气易发生推头、甩尾，造成安全事故");
        alarmDetailMap.put(0x0108L, "已连续行驶4小时，请休息20分钟再行驶");
//        alarmDetailMap.put(0x0107L, "路段限速：70公里/小时||实际速度：%s公里/小时");
        alarmDetailMap.put(0x0107L, "预警时速：%s公里/小时，实际车速：%s公里/小时");
        alarmDetailMap.put(0x0104L, "欢迎使用已开始采集车辆数据");
        alarmDetailMap.put(0x0103L, "终端被拆卸期间将无法采集车辆数据");
        alarmDetailMap.put(0x9999L, "故障码：%s");
        alarmDetailMap.put(0x0105L, "建议电压不低于 12.2 V，当时电压： %s V");
        alarmDetailMap.put(0x9995L, "围栏名称：%s");
        alarmDetailMap.put(0x9994L, "围栏名称：%s");
        alarmDetailMap.put(0x9993L, "本次行驶%s公里");
        alarmDetailMap.put(0x9992L, "停留时长：%d分钟||围栏名称：%s");
//        alarmDetailMap.put(0x0114L, "车辆碰撞");
//        alarmDetailMap.put(0x0106L, "怠速时间过长");
        alarmDetailMap.put(0x8800L, "DSM-疲劳驾驶");
        alarmDetailMap.put(0x8801L, "DSM-分神");
        alarmDetailMap.put(0x8802L, "DSM-打电话");
        alarmDetailMap.put(0x8803L, "DSM-抽烟");
        alarmDetailMap.put(0x8804L, "ADAS-前车碰撞");
        alarmDetailMap.put(0x8805L, "ADAS-行人碰撞");
        alarmDetailMap.put(0x8806L, "ADAS-车道偏离");
        alarmDetailMap.put(0x8807L, "ADAS-车距过近");
    }

    public static Set<Long> onOffAlarmSet = new HashSet<>(); // 车辆启停
    static {
        onOffAlarmSet.add(0x0001L); // 点火
        onOffAlarmSet.add(0x0002L); // 熄火
    }
    public static Set<Long> driveAlarmSet = new HashSet<>(); // 驾驶预警
    static {
        driveAlarmSet.add(0x0111L); // 急加速
        driveAlarmSet.add(0x0112L); // 急减速
        driveAlarmSet.add(0x0113L); // 急转弯
        driveAlarmSet.add(0x0108L); // 疲劳驾驶
        driveAlarmSet.add(0x0107L); // 超速
        driveAlarmSet.add(0x8800L); // 疲劳驾驶
        driveAlarmSet.add(0x8801L); // 分神
        driveAlarmSet.add(0x8802L); // 打电话
        driveAlarmSet.add(0x8803L); // 抽烟
    }
    public static Set<Long> carAlarmSet = new HashSet<>(); // 车辆预警
    static {
        carAlarmSet.add(0x0104L); // 设备插入
        carAlarmSet.add(0x0103L); // 设备拔出
        carAlarmSet.add(0x9999L); // 故障
        carAlarmSet.add(0x0105L); // 低电压
        carAlarmSet.add(0x9994L); // 禁止驶入
        carAlarmSet.add(0x9995L); // 禁止驶出
        carAlarmSet.add(0x9993L); // 无任务外出
        carAlarmSet.add(0x9992L); // 禁停区停留
//        carAlarmSet.add(0x0114L); // 碰撞报警
//        carAlarmSet.add(0x0106L); // 怠速过长
        carAlarmSet.add(0x8804L); // 前车碰撞
        carAlarmSet.add(0x8805L); // 行人碰撞
        carAlarmSet.add(0x8806L); // 车道偏离
        carAlarmSet.add(0x8807L); // 车距过近
    }
    public static Set<Long> highAlarmSet = new HashSet<>(); // 高风险
    static {
        highAlarmSet.add(0x0103L); //终端被拆卸
        highAlarmSet.add(0x8800L); //DSM 疲劳驾驶
        highAlarmSet.add(0x8804L); //ADAS 前车碰撞
        highAlarmSet.add(0x8805L); //ADAS 行人碰撞
    }
    public static Set<Long> middleAlarmSet = new HashSet<>(); // 中风险
    static {
        middleAlarmSet.add(0x0108L); // 疲劳驾驶
        middleAlarmSet.add(0x0107L); // 超速驾驶
        middleAlarmSet.add(0x9999L); // 故障
        middleAlarmSet.add(0x0105L); // 电瓶电压低
        middleAlarmSet.add(0x9994L); // 禁止驶入
        middleAlarmSet.add(0x9995L); // 禁止驶出
        middleAlarmSet.add(0x9992L); // 禁停区停留
        middleAlarmSet.add(0x8801L); // DSM 分神
        middleAlarmSet.add(0x8802L); // DSM 打电话
        middleAlarmSet.add(0x8803L); // DSM 抽烟
        middleAlarmSet.add(0x8806L); // ADAS 车道偏离
        middleAlarmSet.add(0x8807L); // ADAS 车距过近
    }
    public static Set<Long> lowAlarmSet = new HashSet<>(); // 低风险
    static {
        lowAlarmSet.add(0x0111L); // 急加速
        lowAlarmSet.add(0x0112L); // 急减速
        lowAlarmSet.add(0x0113L); // 急转弯
        lowAlarmSet.add(0x9993L); // 无任务外出
    }

    public static Set<Long> tipAlarmSet = new HashSet<>(); // 提示
    static {
        tipAlarmSet.add(1L); // 点火
        tipAlarmSet.add(2L); // 熄火
        tipAlarmSet.add(0x0104L); // 插入
    }

    public static Map<Integer, String> alarmLevelMap = new HashMap<>();

    static {
        // 1 高风险 2 中风险 3 低风险 4 提示
        alarmLevelMap.put(1, "高风险");
        alarmLevelMap.put(2, "中风险");
        alarmLevelMap.put(3, "低风险");
        alarmLevelMap.put(4, "提示");
    }

    public static Map<Integer, String> overSpeedConfigMap = new HashMap<>();

    static {
        //公路等级 1 高速公路 2 一级公路 3 二级公路 4 三级公路 5 四级公路 6 快速道路 7 未分级道路 8 一级公路-辅道 9二级公路-辅道 10 快速道路-辅道
        overSpeedConfigMap.put(1, "高速公路");
        overSpeedConfigMap.put(2, "一级公路");
        overSpeedConfigMap.put(3, "二级公路");
        overSpeedConfigMap.put(4, "三级公路");
        overSpeedConfigMap.put(5, "四级公路");
        overSpeedConfigMap.put(6, "快速道路");
        overSpeedConfigMap.put(7, "未分级道路");
        overSpeedConfigMap.put(8, "一级公路-辅道");
        overSpeedConfigMap.put(9, "二级公路-辅道");
        overSpeedConfigMap.put(10, "快速道路-辅道");
    }

    public static Map<Integer, Integer> defaultOverSpeedMap = new HashMap<>();

    static {
        //公路等级 1 高速公路 2 一级公路 3 二级公路 4 三级公路 默认开启
        defaultOverSpeedMap.put(1, 132);
        defaultOverSpeedMap.put(2, 110);
        defaultOverSpeedMap.put(3, 88);
        defaultOverSpeedMap.put(4, 66);
    }

    public static Map<String, Integer> overSpeedHttpLevelMap = new HashMap<>();

    static {
//        ['motorway'] = ['高速公路']  [限速, 道路等级分数]
//        ['trunk'] = ['快速道路']
//        ['primary'] = ['一级公路']
//        ['secondary'] = ['二级公路']
//        ['tertiary'] = ['三级公路']
//        ['unclassified'] = ['未分级道路']
//        ['track'] = ['农业道路']
//        ['residential'] = ['四级道路']
//        ['motorway_link'] = ['高速公路-辅道']
//        ['trunk_link'] = ['快速道路-辅道']
//        ['primary_link'] = ['一级公路-辅道']
//        ['secondary_link'] =['二级公路-辅道']
//        ['motorway_junction'] =['高速公路枢纽']
//        ['pedestrian'] = ['步行街']
        overSpeedHttpLevelMap.put("motorway",1);
        overSpeedHttpLevelMap.put("trunk",6);
        overSpeedHttpLevelMap.put("primary", 2);
        overSpeedHttpLevelMap.put("secondary", 3);
        overSpeedHttpLevelMap.put("tertiary", 4);
        overSpeedHttpLevelMap.put("unclassified", 7);
        overSpeedHttpLevelMap.put("track", 7);
        overSpeedHttpLevelMap.put("residential", 5);
        overSpeedHttpLevelMap.put("motorway_link", 7);
        overSpeedHttpLevelMap.put("trunk_link", 10);
        overSpeedHttpLevelMap.put("primary_link", 8);
        overSpeedHttpLevelMap.put("secondary_link", 9);
        overSpeedHttpLevelMap.put("motorway_junction", 7);
        overSpeedHttpLevelMap.put("pedestrian", 7);
    }

    public static Set<Long> screenEvents = new HashSet<>();
    static {
        screenEvents.add(0x0111l);
        screenEvents.add(0x0112l);
        screenEvents.add(0x0113l);
        screenEvents.add(0x0107l);
    }
}
