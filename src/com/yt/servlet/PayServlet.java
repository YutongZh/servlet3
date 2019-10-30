package com.yt.servlet;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 同步和异步
 */
@WebServlet(value = "/pay", asyncSupported = true)
public class PayServlet extends HttpServlet {


    /**
     * 主线程执行先释放，然后副线程执行返回结果
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("主线程开始执行:::" + Thread.currentThread() + ":::::::" + System.currentTimeMillis());

        AsyncContext asyncContext = req.startAsync();
        asyncContext.start(() -> {
            System.out.println("副线程开始执行:::" + Thread.currentThread() + ":::::::" + System.currentTimeMillis());
            doBusiness();
            //asyncContext.complete(); //异步操作执行结束 通知容器
            AsyncContext reqAsyncContext = req.getAsyncContext();

            ServletResponse response = reqAsyncContext.getResponse();
            try {
                response.getWriter().write("hello world");
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("副线程结束执行:::" + Thread.currentThread() + ":::::::" + System.currentTimeMillis());
        });
        System.out.println("主线程结束执行:::" + Thread.currentThread() + ":::::::" + System.currentTimeMillis());

    }

    private void doBusiness() {
        try {
            System.out.println("doing" + Thread.currentThread());
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
