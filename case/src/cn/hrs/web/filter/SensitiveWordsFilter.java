package cn.hrs.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

@WebFilter("/*")
public class SensitiveWordsFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        // 1.创建代理对象，增强getParameter方法
        ServletRequest proxy_req = (ServletRequest)Proxy.newProxyInstance(req.getClass().getClassLoader(), req.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 判断方法是否为getParameter
                if (method.getName().equals("getParameter")) {
                    // 获取返回值
                    String value = (String)method.invoke(req, args);
                    if (value != null){
                        // 遍历敏感词list
                        for (String str : list){
                            if (value.contains(str)){
                                value = value.replaceAll(str, "***");
                                System.out.println(value);
                            }
                        }
                    }
                    return value;
                }
                // 判断方法是否为getParameterMap
                if (method.getName().equals("getParameterMap")){
                    // 获取返回值
                    Map<String,String[]> map = (Map<String,String[]>)method.invoke(req, args);
                    // 新建一个map对象
                    Map<String, String[]> paramap = new HashMap<>();
                    if (map != null){
                        // 遍历敏感词(list)
                        for (String str:list){
                            // 遍历键(key)
                            for (String key : map.keySet()){
                                String[] values = map.get(key);
                                if (values != null){
                                    // 遍历值(values)
                                    for (int i = 0;i < values.length;i++){
                                        if (values[i].contains(str)){
                                            values[i] = values[i].replaceAll(str,"***");
                                        }
                                    }
                                }

                                paramap.put(key,values);
                            }
                        }
                    }
                    return paramap;
                }
                // 判断方法是否是getParameterValues
                if (method.getName().equals("getParameterValues")){
                    // 获取返回值
                    String[] values = (String[]) method.invoke(req, args);
                    if (values != null){
                        for (String str : list){
                            for (int i = 0;i < values.length;i++){
                                if (values[i].contains(str)){
                                    values[i] = values[i].replaceAll(str,"***");
                                }
                            }
                        }
                    }
                    return values;
                }
                // 不是getParameter方法
                return method.invoke(req,args);
            }
        });
        // 2.放行
        chain.doFilter(proxy_req, resp);
    }
    private List<String> list = new ArrayList<>();
    public void init(FilterConfig config) throws ServletException {
        try {
            // 1.获取敏感词文件路径
            ServletContext servletContext = config.getServletContext();
            String path = servletContext.getRealPath("/WEB-INF/classes/敏感词汇.txt");
            // 2.读取文件数据
            BufferedReader br = new BufferedReader(new FileReader(path));
            // 3.将文件的每一行添加到list
            String line = null;
            while ((line=br.readLine()) != null){
                list.add(line);
            }
            br.close();
            System.out.println(list);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}
