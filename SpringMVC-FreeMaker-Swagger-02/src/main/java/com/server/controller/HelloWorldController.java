package com.server.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.server.common.CommonUtils;
import com.server.dto.PeopleDTO;
import com.server.vo.PeopleVO;
import com.server.vo.SomeThingVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 初始页面：
 * http://localhost:8090/home
 * <p>
 * 本地Swagger:
 * http://127.0.0.1:8085/swagger/index.html#/
 * <p>
 * 注意：
 * https://github.com/swagger-api/swagger-ui 下载后dist放入webapp-swagger目录
 * <p>
 * 参考：
 * https://www.cnblogs.com/cq-jiang/p/8457770.html
 * <p>
 * '@Api':swagger分类标题注解
 *
 * @author CYX
 * @create 2018-04-25-6:35
 */
@Controller
@Api(tags = "Demo首页")
public class HelloWorldController {

    private static final Logger logger = LoggerFactory.getLogger(HelloWorldController.class);

    public static Gson gson = (new GsonBuilder()).enableComplexMapKeySerialization().create();

    @Autowired
    private CommonUtils commonUtils;

    /**
     * 进入初始页面
     * <p>
     * '@ApiResponses':swagger返回值注解
     * <p>
     * '@ApiOperation':swagger当前接口注解
     *
     * @param model
     * @return
     */
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "系统错误"),
            @ApiResponse(code = 200, message = "0-成功,展示首页,其它为错误", response = String.class)})
    @ApiOperation(httpMethod = "GET", value = "初始页面")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String sayHelloWorld(Model model) {
        logger.info("sayHelloWorld");
        model.addAttribute("information", "哈哈哈哈哈哈");
        return "home.ftl";
    }

    /**
     * 测试ajax,接收后台数据-替换HTML标签
     * <p>
     * '@ApiResponses':swagger返回值注解
     * <p>
     * '@ApiOperation':swagger当前接口注解
     *
     * @param userName
     * @param request
     * @param response
     */
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "系统错误"),
            @ApiResponse(code = 200, message = "0-成功", response = String.class)})
    @ApiOperation(httpMethod = "GET", value = "测试ajax,接收后台数据-替换HTML标签")
    @RequestMapping(value = "/getInformation", method = RequestMethod.GET)
    public void getInformation(String userName, HttpServletRequest request, HttpServletResponse response) {
        logger.info("getInformation");
        logger.info("userName : " + userName);
        Map<String, String> jsonMap = new HashMap<String, String>();
        jsonMap.put("returnCode", "4000");
        jsonMap.put("name", "cyx111");
        String resultJson = gson.toJson(jsonMap);
        commonUtils.flushResultToPage(response, resultJson);
    }

    /**
     * 测试数据提交-多个字段封装对象提交
     * <p>
     * '@ApiResponses':swagger返回值注解
     * <p>
     * '@ApiOperation':swagger当前接口注解
     *
     * @param peopleDTO
     * @param request
     * @param response
     */
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "系统错误"),
            @ApiResponse(code = 200, message = "0-成功", response = String.class)})
    @ApiOperation(httpMethod = "POST", value = "测试数据提交-多个字段封装对象提交")
    @RequestMapping(value = "/testFormSubmission", method = RequestMethod.POST)
    public void testFormSubmission(@RequestBody PeopleDTO peopleDTO, HttpServletRequest request, HttpServletResponse response) {
        logger.info("testFormSubmission");
        logger.info("peopleDTO : " + peopleDTO.toString());
        commonUtils.flushResultToPage(response, "9527");

    }

    /**
     * 测试表单提交-多个字段提交
     * <p>
     * '@ApiResponses':swagger返回值注解
     * <p>
     * '@ApiOperation':swagger当前接口注解
     *
     * @param userName
     * @param userAge
     * @param userAddress
     * @param response
     */
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "系统错误"),
            @ApiResponse(code = 200, message = "0-成功", response = String.class)})
    @ApiOperation(httpMethod = "POST", value = "测试表单提交-多个字段提交")
    @RequestMapping(value = "/testFormSubmission2", method = RequestMethod.POST)
    public void testFormSubmission2(String userName, String userAge, String userAddress, HttpServletResponse response) {
        logger.info("userName : " + userName);
        logger.info("userAge : " + userAge);
        logger.info("userAddress : " + userAddress);
        commonUtils.flushResultToPage(response, "9528");
    }

    /**
     * 测试ajax调用后台，接口后台页面，简单数据
     * <p>
     * '@ApiResponses':swagger返回值注解
     * <p>
     * '@ApiOperation':swagger当前接口注解
     *
     * @param request
     * @param response
     */
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "系统错误"),
            @ApiResponse(code = 200, message = "0-成功", response = String.class)})
    @ApiOperation(httpMethod = "GET", value = "测试ajax调用后台，接口后台页面，简单数据")
    @RequestMapping(value = "/backToThePage", method = RequestMethod.GET)
    public void backToThePage(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("backToThePage");
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userName", "99999");
        String html = commonUtils.getOutputHtmlStr(map, "backToThePage.ftl");
        jsonMap.put("returnCode", "4001");
        jsonMap.put("html", html);
        String resultJSON = gson.toJson(jsonMap);
        commonUtils.flushResultToPage(response, resultJSON);
    }

    /**
     * 测试ajax调用后台，接口后台页面，复杂数据
     * <p>
     * '@ApiResponses':swagger返回值注解
     * <p>
     * '@ApiOperation':swagger当前接口注解
     *
     * @param request
     * @param response
     */
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "系统错误"),
            @ApiResponse(code = 200, message = "0-成功", response = String.class)})
    @ApiOperation(httpMethod = "GET", value = "测试ajax调用后台，接口后台页面，复杂数据")
    @RequestMapping(value = "/backToThePageComple", method = RequestMethod.GET)
    public void backToThePageComple(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("backToThePageComple");
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userName", "88888");

        SomeThingVO someThingVO = new SomeThingVO();
        List<PeopleVO> peopleVOs = new ArrayList<PeopleVO>();
        for (int i = 0; i < 5; i++) {
            PeopleVO peopleVO = new PeopleVO();
            peopleVO.setName("name" + i);
            peopleVO.setAge("2" + i);
            peopleVO.setAddress("南京" + i);
            peopleVO.setSix("男" + i);
            peopleVOs.add(peopleVO);
        }
        someThingVO.setPeopleVO(peopleVOs);
        someThingVO.setId("001");
        map.put("someThingVO", someThingVO);

        String html = commonUtils.getOutputHtmlStr(map, "backToThePageComple.ftl");
        logger.info("html : " + html);
        jsonMap.put("html", html);
        String resultJSON = gson.toJson(jsonMap);
        commonUtils.flushResultToPage(response, resultJSON);
    }

    /**
     * 测试SpringMVC文件上传
     * <p>
     * '@ApiResponses':swagger返回值注解
     * <p>
     * '@ApiOperation':swagger当前接口注解
     *
     * @param multipartFile
     * @param request
     * @return
     */
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "系统错误"),
            @ApiResponse(code = 200, message = "0-成功", response = String.class)})
    @ApiOperation(httpMethod = "POST", value = "测试SpringMVC文件上传")
    @RequestMapping(value = "/upload.do")
    public String uploads(@RequestParam(value = "file", required = false) MultipartFile multipartFile, HttpServletRequest request) {

        String path = request.getSession().getServletContext().getRealPath("upload");
        String fileName = multipartFile.getOriginalFilename();
        File targetFile = new File(path, fileName);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        logger.info(targetFile.toString());
        try {
            multipartFile.transferTo(targetFile);
            //TODO 文件解析等操作....
            String fileResult = FileUtils.readFileToString(targetFile, "UTF-8");
            logger.info(fileResult);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "hello.ftl";
    }

}
