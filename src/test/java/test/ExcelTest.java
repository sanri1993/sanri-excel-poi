package test;

import com.sanri.excel.poi.ExcelExportWriter;
import com.sanri.excel.poi.ExcelImportUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;
import vo.ExtendSimple;
import vo.Simple;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ExcelTest {
    /**
     * 简单多类型 Excel 生成
     */
    @Test
    public void testSimpleBean() throws IOException {
        ExcelExportWriter excelExportWriter = new ExcelExportWriter(Simple.class);

        List<Simple> simpleList = simpleBeanDatas(10);
        excelExportWriter.export(simpleList);
        excelExportWriter.writeTo(new FileOutputStream("d:/test/"+System.currentTimeMillis()+".xlsx"));
    }

    /**
     * 测试继承 bean Excel 生成
     */
    @Test
    public void testExtendBean() throws IOException {
        ExcelExportWriter excelExportWriter = new ExcelExportWriter(ExtendSimple.class);

        List<Simple> simpleList = simpleBeanDatas(10);
        List<ExtendSimple> extendSimples = new ArrayList<ExtendSimple>();
        for (Simple simple : simpleList) {
            ExtendSimple extendSimple = new ExtendSimple(simple);
            extendSimple.setIdcard(RandomUtil.idcard());
            extendSimples.add(extendSimple);
        }
        excelExportWriter.export(extendSimples);
        excelExportWriter.writeTo(new FileOutputStream("d:/test/"+System.currentTimeMillis()+".xlsx"));
    }

    @Test
    public void testImportExcel() throws IOException {
        FileInputStream fileInputStream = FileUtils.openInputStream(new File("D:\\test/1567833427823.xlsx"));
        List<Simple> simples = ExcelImportUtil.importData(fileInputStream, Simple.class);
        for (Simple simple : simples) {
            System.out.println(ToStringBuilder.reflectionToString(simple, ToStringStyle.SHORT_PREFIX_STYLE));
        }
    }

    @Test
    public void testImportExtendExcel() throws IOException {
        FileInputStream fileInputStream = FileUtils.openInputStream(new File("D:\\test/1567833685699.xlsx"));
        List<ExtendSimple> simples = ExcelImportUtil.importData(fileInputStream, ExtendSimple.class);
        for (Simple simple : simples) {
            System.out.println(ToStringBuilder.reflectionToString(simple, ToStringStyle.SHORT_PREFIX_STYLE));
        }
    }

    /**
     * 测试单列导入
     * @throws IOException
     */
    @Test
    public void testImportList() throws IOException {
        FileInputStream fileInputStream = FileUtils.openInputStream(new File("D:\\test/1567833685699.xlsx"));
        List<String> strings = ExcelImportUtil.importListData(fileInputStream, String.class, 0, 1);
        System.out.println(strings);
    }

    @Test
    public void testImportMap() throws IOException {
        FileInputStream fileInputStream = FileUtils.openInputStream(new File("D:\\test/1567833685699.xlsx"));
        Map<String, String> stringStringMap = ExcelImportUtil.importMapData(fileInputStream, String.class, 0, 8, 1);
        System.out.println(stringStringMap);
    }

    private List<Simple> simpleBeanDatas(int count) {
        List<Simple> simples = new ArrayList<Simple>();
        for (int i = 0; i < count; i++) {
            String username = RandomUtil.username();
            int age = RandomUtils.nextInt(25,140);
            int level = RandomUtils.nextInt(1,4);
            Date birthday = RandomUtil.date();
            long id = RandomUtils.nextLong();
            boolean success = RandomUtils.nextBoolean();
            double money = RandomUtils.nextDouble(1,10);
            float comm = RandomUtils.nextFloat(1,10);

            Simple simple = new Simple(age, level, username, birthday, id, success, money,comm);
            simples.add(simple);
        }

        return simples;
    }

}
