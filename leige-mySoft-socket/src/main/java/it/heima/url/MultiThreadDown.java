package it.heima.url;

public class MultiThreadDown
{
    private static final String path="https://fkjava.org/2020/04/01/ssm/ssm_cover.png";
    private static final String targetFile="D:\\FTPShare\\17\\17.2\\target\\ios.png";
    public static void main(String[] args) throws Exception
    {
        // 初始化DownUtil对象
//        final DownUtil downUtil = new DownUtil("http://www.crazyit.org/"
//                + "attachments/month_1403/1403202355ff6cc9a4fbf6f14a.png"
//                , "ios.png", 4);
        final DownUtil downUtil = new DownUtil(path, targetFile, 4);
        // 开始下载
        downUtil.download();
        new Thread(() -> {
            while(downUtil.getCompleteRate() < 1)
            {
                // 每隔0.1秒查询一次任务的完成进度，
                // GUI程序中可根据该进度来绘制进度条
                System.out.println("已完成："
                        + downUtil.getCompleteRate());
                try
                {
                    Thread.sleep(1000);
                }
                catch (Exception ex){}
            }
        }).start();
    }
}
