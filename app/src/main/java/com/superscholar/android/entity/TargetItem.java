package com.superscholar.android.entity;

import com.superscholar.android.tools.BoundsTime;
import com.superscholar.android.tools.Date;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017/3/5.
 */

public class TargetItem implements Serializable{

    private UUID uuid;//识别码
    private String name;//目标名
    private int timesPerWeek;//每周次数
    private int lastedWeek;//持续周数
    private boolean isCheck=true;//是否打卡检测
    private boolean needRemind=true;//是否需要提醒，isCheck=true时neeedRemind必须为true
    private BoundsTime remindTime;//提醒的时间，needRemind=false时为空
    private Date startDate;//目标开始日期
    private int signDays=0;//打卡总天数
    private int consecutiveSignDays=0;//连续打卡天数
    private boolean isTodaySign=false;//今天是否打卡
    private boolean isYesterdaySign=false;//昨天是否打卡
    private int []weekSignTimes;//持续的周中各周的打卡次数
    private boolean isValid=true;//是否有效，达到周数后为false
    private List<Date>signDates;//打卡日期列表

    //检测或非检测需要提醒的构造方法
    public TargetItem(String name,int timesPerWeek,int lastedWeek,boolean isCheck,int hour,int min,Date startDate){
        this.uuid=UUID.randomUUID();
        this.name=name;
        this.timesPerWeek=timesPerWeek;
        this.lastedWeek=lastedWeek;
        this.isCheck=isCheck;
        this.remindTime=new BoundsTime(hour,min);
        this.startDate=startDate;
        this.weekSignTimes=new int[lastedWeek];
        this.signDates=new ArrayList<>();
    }

    //非检测不需要提醒的构造方法
    public TargetItem(String name,int timesPerWeek,int lastedWeek,Date startDate){
        this.uuid=UUID.randomUUID();
        this.name=name;
        this.timesPerWeek=timesPerWeek;
        this.lastedWeek=lastedWeek;
        this.isCheck=false;
        this.needRemind=false;
        this.remindTime=null;
        this.startDate=startDate;
        this.weekSignTimes=new int[lastedWeek];
        this.signDates=new ArrayList<>();
    }

    public TargetItem(){}

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimesPerWeek() {
        return timesPerWeek;
    }

    public void setTimesPerWeek(int timesPerWeek) {
        this.timesPerWeek = timesPerWeek;
    }

    public int getLastedWeek() {
        return lastedWeek;
    }

    public void setLastedWeek(int lastedWeek) {
        this.lastedWeek = lastedWeek;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public boolean isNeedRemind() {
        return needRemind;
    }

    public void setNeedRemind(boolean needRemind) {
        this.needRemind = needRemind;
    }

    public BoundsTime getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(BoundsTime remindTime) {
        this.remindTime = remindTime;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getSignDays() {
        return signDays;
    }

    public void setSignDays(int signDays) {
        this.signDays = signDays;
    }

    public int getConsecutiveSignDays() {
        return consecutiveSignDays;
    }

    public void setConsecutiveSignDays(int consecutiveSignDays) {
        this.consecutiveSignDays = consecutiveSignDays;
    }

    public boolean isTodaySign() {
        return isTodaySign;
    }

    public void setTodaySign(boolean todaySign) {
        isTodaySign = todaySign;
    }

    public boolean isYesterdaySign() {
        return isYesterdaySign;
    }

    public void setYesterdaySign(boolean yesterdaySign) {
        isYesterdaySign = yesterdaySign;
    }

    public int[] getWeekSignTimes() {
        return weekSignTimes;
    }

    public void setWeekSignTimes(int[] weekSignTimes) {
        this.weekSignTimes = weekSignTimes;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public List<Date> getSignDates() {
        return signDates;
    }

    public void setSignDates(List<Date> signDates) {
        this.signDates = signDates;
    }
}
