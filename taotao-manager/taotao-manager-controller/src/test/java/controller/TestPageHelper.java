package controller;

/*import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;*/

public class TestPageHelper {

    /*@Test
    public void testPageHelper() {

        // 创建Spring容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/spring/applicationContext-*.xml");

        // 从Spring容器中获取代理对象
        TbItemMapper tbItemMapper = applicationContext.getBean(TbItemMapper.class);

        // 执行查询并分页
        TbItemExample tbItemExample = new TbItemExample();
        PageHelper.startPage(1, 10);
        List<TbItem> tbItems = tbItemMapper.selectByExample(tbItemExample);

        // 查询总行数
        PageInfo<TbItem> pageInfo = new PageInfo<>(tbItems);
        long total = pageInfo.getTotal();
        // 打印
        for (TbItem tbItem : tbItems) {
            System.out.println(tbItem.getTitle());
        }
        System.out.println("总商品数：" + total);

        //
    }*/

}
