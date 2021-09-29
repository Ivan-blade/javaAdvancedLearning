package com.ivan.filter;

import com.ivan.pojo.MethodInfo;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


//@Activate(group = {CommonConstants.CONSUMER,CommonConstants.PROVIDER})
@Activate(group = {CommonConstants.CONSUMER})
public class DubboInvokeFilter implements Filter,Runnable {


    Map<String, List<MethodInfo>> methodTimes = new ConcurrentHashMap<>();

    public DubboInvokeFilter(){
        // 创建定时线程，每隔5s打印一次最近1分钟内每个方法的TP90、TP99的耗时情况
        Executors.newSingleThreadScheduledExecutor()
                .scheduleWithFixedDelay(this, 5,5, TimeUnit.SECONDS);
    }


    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        long startTime = System.currentTimeMillis();
        long takeTime = 0l;
        Result result = null;
        try {
            // 执行方法
            result =  invoker.invoke(invocation);
            takeTime = System.currentTimeMillis()-startTime;
            String methodName = invocation.getMethodName();

            List<MethodInfo> methodInfos =  methodTimes.get(methodName);
            //方法执行第一次的时候，创建空集合进行存储
            if (methodInfos == null){
                methodInfos = new ArrayList<>();
                methodTimes.put(methodName,methodInfos);
            }
            //将当前的方法添加到map中指定的记录当前方法执行情况的集合中
            methodInfos.add(new MethodInfo(invocation.getMethodName(),takeTime,System.currentTimeMillis()));
        } finally {
            System.out.println("invoke time:"+ takeTime + "毫秒");
            return result;
        }

    }

    /**
     * 线程方法
     */
    @Override
    public void run() {
        Date date = new Date();
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dateStr = sdf.format(date);
        /**
         * 线程使用情况
         */
        for(Map.Entry<String,List<MethodInfo>> methodInfos : methodTimes.entrySet()){
            System.out.println(dateStr+ methodInfos.getKey() +"的TP90:" + getTP(methodInfos.getValue(),0.9) + "毫秒,"
                    + "TP99:" + getTP(methodInfos.getValue(),0.99)+ "毫秒" );
        }
    }

    /**
     * 后续会每隔5s调用一次此方法，计算并打印最近1分钟内每个方法的TP90、TP99的耗时情况
     *
     * 计算tp90和tp99
     * @param methodInfos
     * @param rate 代表百分比 90 传入 0.9 即可  99 就是 0.99
     * @return
     */
    private long getTP(List<MethodInfo> methodInfos, double  rate){
        // 构建一个临时集合保存 用于满足1一分钟之内的数据
        List<MethodInfo>  sortInfo = new ArrayList<>();
        // 计算最近一分钟的TP90 和 TP99
        long  endTime = System.currentTimeMillis();
        long  startTime = System.currentTimeMillis() - 60000;

        // 遍历列表集合
        int  length = methodInfos.size();
        for (int i=0;i<length;i++){
            MethodInfo methodInfo = methodInfos.get(i);
            if (methodInfo.getEndTimes() >= startTime && methodInfo.getEndTimes() <= endTime){
                //将满足条件的方法信息存储到临时集合中
                sortInfo.add(methodInfo);
            }
        }

        //对满足1一分钟之内的数据进行排序
        sortInfo.sort(new Comparator<MethodInfo>() {
            @Override
            public int compare(MethodInfo o1, MethodInfo o2) {
                if(o1.getTimes() > o2.getTimes()){
                    return  1;
                }else if(o1.getTimes() < o2.getTimes()){
                    return -1;
                }else{
                    return  0;
                }

            }
        });

        //获取当前排序后集合中的指定百分比数值的位置，此位置存储的数据就是当前计算的tp90/99
        int  index = (int)(sortInfo.size() * rate);

        return sortInfo.get(index).getTimes();
    }
}
