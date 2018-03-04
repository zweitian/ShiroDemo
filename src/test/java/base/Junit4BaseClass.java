package base;

import junit.framework.TestCase;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author:ztian
 * @Description:
 * @CreateTime: 2018/3/4  13:41
 */
// 使用junit4进行测试
@RunWith(SpringJUnit4ClassRunner.class)
// 加载配置文件
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class Junit4BaseClass extends TestCase{
}
