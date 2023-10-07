package com.shenxi.psych.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.shenxi.psych.entity.*;
import com.shenxi.psych.service.AdminService;
import com.shenxi.psych.service.DoctorService;
import com.shenxi.psych.service.ResourcesService;
import com.shenxi.psych.service.PatientService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author shenxi
 * @Date 2023/9/29 16:39
 * @Version 1.0
 */
@Controller
public class AdminController {
    private final static Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    AdminService adminService;

    @Autowired
    ResourcesService resourcesService;

    @Autowired
    PatientService patientService;

    @Autowired
    DoctorService doctorService;

    /**
     * 跳转到登录页面
     * @param httpServletRequest
     * @return
     */
    @GetMapping("/admin")
    public String loginPage(HttpServletRequest httpServletRequest){
        String s = httpServletRequest.getParameter("success");
        httpServletRequest.setAttribute("success",s);
        return "/user/sign-in-admin";
    }

    /**
     * 校验管理员身份（id + password）
     * @return
     */
    @GetMapping("/admin/adminChecked")
    @ResponseBody
    public Admin AdminChecked(Admin admin){
        logger.info("admin的信息->{}", JSON.toJSON(admin));
        return adminService.checkAdmin(admin);
    }

    /**
     * 跳转到主管理员主页
     * @param admin
     * @return
     */
    @GetMapping("/admin/toHomePage")
    public String toHomePage(Admin admin,HttpServletRequest request){
        admin = adminService.getAdminByName(admin.getName());
        request.getSession().setAttribute("admin",admin);
        return "/admin/home";
    }

    /**
     * 跳转到用户管理
     * @return
     */
    @GetMapping("/admin/toUserManager")
    public String toUserManager(@RequestParam(required = false,defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "4",value = "pageSize") Integer pageSize, Model model){

        PageInfo<Patient> students = patientService.getAllPatient(pageNum, pageSize);
        PageInfo<Doctor> doctors = doctorService.getAllDoctor(pageNum, pageSize);
        PageInfo<Admin> admins = adminService.getAllAdmin(pageNum, pageSize);

        model.addAttribute("stuPageInfo",students);
        model.addAttribute("docPageInfo",doctors);
        model.addAttribute("admPageInfo",admins);
        return "/admin/userManager";
    }

    @GetMapping("/admin/findStuPage")
    public String stuPage(@RequestParam(required = false,defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "4",value = "pageSize") Integer pageSize,Model model){
        PageInfo<Patient> allPatient = patientService.getAllPatient(pageNum, pageSize);
        logger.info("分页查找->{}",JSON.toJSON(allPatient));
        model.addAttribute("stuPageInfo",allPatient);
        return "/admin/stuPage";
    }

    @GetMapping("/admin/findDocPage")
    public String docPage(@RequestParam(required = false,defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "4",value = "pageSize") Integer pageSize,Model model){
        PageInfo<Doctor> docPageInfo = doctorService.getAllDoctor(pageNum, pageSize);
        logger.info("分页查找->{}",JSON.toJSON(docPageInfo));
        model.addAttribute("docPageInfo",docPageInfo);
        return "/admin/doctorPage";
    }

    @GetMapping("/admin/findAdminPage")
    public String adminPage(@RequestParam(required = false,defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "4",value = "pageSize") Integer pageSize,Model model){
        PageInfo<Admin> adminPageInfo = adminService.getAllAdmin(pageNum, pageSize);
        logger.info("分页查找->{}",JSON.toJSON(adminPageInfo));
        model.addAttribute("admPageInfo",adminPageInfo);
        return "/admin/adminPage";
    }

    /**
     * 跳转到资源管理
     * @return
     */
    @GetMapping("/admin/toResourceManager")
    public String toResourceManager(Model model){

        List<Document> documents = resourcesService.getAllDocument();
        model.addAttribute("documents",documents);
        return "/admin/resourceManager";
    }

    @GetMapping("/admin/toActivities")
    public String toActivityPage(HttpServletRequest request){
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        request.setAttribute("admin",admin);
        return "/admin/activities";
    }

    @GetMapping("/admin/toActivitiesUpload")
    public String toActivitiesUpload(){
        return "/admin/activities-upload";
    }

    @GetMapping("/admin/toDocumentUpload")
    public String toDocumentUpload(){
        return "/admin/document-upload";
    }

    /**
     * 跳转到上传专栏
     * @param request
     * @throws IOException
     */
    @PostMapping("/admin/postDocument")
    public String addDocument(HttpServletRequest request) throws IOException {
        String title = request.getParameter("title");
        if(StringUtils.isEmpty(title)) {
            throw new RuntimeException("文章标题不能为空");
        }
        String creator = request.getParameter("creator");
        if(StringUtils.isEmpty(title)) {
            throw new RuntimeException("文章作者不能为空");
        }
        String content = request.getParameter("content");
        if(StringUtils.isEmpty(title)) {
            throw new RuntimeException("文章内容不能为空");
        }
        String url = request.getParameter("url");
        //得到专栏标签
        String tags = request.getParameter("tags");
        Document document = new Document();
        document.setTitle(title);
        document.setContent(content);
        document.setCreator(creator);
        document.setUrl(url);
        document.setViewCount(0);
        String token[] = tags.split(",");
        List<Tag> tagList = new ArrayList<>();
        for (String t:token){
            Tag tag = new Tag();
            tag.setId(Integer.valueOf(t));
            tagList.add(tag);
        }
        document.setTags(tagList);
        resourcesService.insertDocument(document);
        return "200";
    }

    @GetMapping(value = "/admin/delectDoctor/{doctorId}")
    public String delectDoctor(@PathVariable Integer doctorId){
        doctorService.delectDoctorById(doctorId);
        return "redirect:/admin/toUserManager";
    }

    @GetMapping(value = "/admin/delectAdmin/{adminId}")
    public String delectAdmin(@PathVariable Integer adminId){
        adminService.deleteAdminById(adminId);
        return "redirect:/admin/toUserManager";
    }

    @GetMapping(value = "/admin/delectStudent/{studentId}")
    public String delectStudent(@PathVariable Integer studentId){
        patientService.deleteStudentById(studentId);
        return "redirect:/admin/toUserManager";
    }

    @GetMapping(value = "/admin/delectDoc//{docId}")
    public String delectDoc(@PathVariable Integer docId){
        resourcesService.deleteDocById(docId);
        return "redirect:/admin/toResourceManager";
    }

    /**
     * 管理员注销登录
     * @param id
     * @param request
     * @return
     */
    @GetMapping(value = "/admin/return")
    public String returnPage(Integer id,HttpServletRequest request){
        request.getSession().setAttribute("admin",null);
        logger.info("咨询者注销成功！");
        return "redirect:/admin";
    }
}
