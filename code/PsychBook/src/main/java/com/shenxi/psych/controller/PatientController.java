package com.shenxi.psych.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import com.shenxi.psych.entity.*;
import com.shenxi.psych.service.*;
import com.shenxi.psych.service.RedisService.UserRedisService;
import com.shenxi.psych.utils.exception.CMSException;
import com.shenxi.psych.utils.exception.ResultCodeEnum;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author shenxi
 * @Date 2023/10/4 20:54
 * @Version 1.0
 */
@Controller
public class PatientController {

    private final static Logger logger = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    private LoginService loginService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private UserRedisService userRedisService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ChatFriendsService chatFriendsService;

    @Autowired
    private ResourcesService resourcesService;
    /**
     * 跳转到登录页面
     * @param httpServletRequest
     * @return
     */
    @GetMapping(value = "/stu")
    public String loginPage(HttpServletRequest httpServletRequest){
        String s = httpServletRequest.getParameter("success");
        httpServletRequest.setAttribute("success",s);
        return "/user/sign-in-stu";
    }

    /**
     * 跳转到注册页面
     * @return
     */
    @GetMapping(value = "/stu/toRegister")
    public String registerPage(){
        return "/user/sign-up-stu";
    }

    /**
     * 完成注册，跳转到登录页面
     * @param patient
     * @return
     */
    @PostMapping(value = "/stu/register")
    @ResponseBody
    public void register(Patient patient){
        logger.info("patient->{}", JSON.toJSON(patient));

        patient.setGmtCreate(System.currentTimeMillis());
        try{
            //插入咨询者登录帐号和聊天帐号
            int res = patientService.insertPatientAndLogin(patient);
            if (res != 0){
                logger.info("成功插入数据，res->{}",res);
            }
        }catch (Exception e){
                logger.error("插入数据失败");
                logger.error(String.valueOf(e.getCause()));
        }

    }

    /**
     * 校验咨询者身份（id）
     * @return
     */
    @GetMapping(value = "/stu/stuChecked1")
    @ResponseBody
    public Patient stuCheckedById(Patient patient){
        logger.info("patient's stuNumber is ->{}",patient.getStuNumber());
        String stuNumber = patient.getStuNumber();
        return patientService.getStuByStuNumber(stuNumber);
    }

    /**
     * 校验咨询者身份（id + password）
     * @return
     */
    @GetMapping(value = "/stu/stuChecked")
    @ResponseBody
    public Patient stuChecked(String stuNumber,String password){

        logger.info("stuNumber->{},password->{}",stuNumber,password);
        return patientService.stuChecked(stuNumber,password);
    }

    /**
     * 验证码登录（电话号码 + 验证码）
     * @return
     */
    @GetMapping(value = "/stu/stuAuthcode")
    @ResponseBody
    public Patient stuAuthcode(String tel,String authcode){

        logger.info("tel->{},authcode->{}",tel,authcode);
        return patientService.stuAuthcode(tel,authcode);
    }

    /**
     * 跳转到咨询者主页
     * @param stuNumber
     * @return
     */
    @GetMapping(value = "/stu/toHomePage")
    public String toHomePage(@RequestParam(required = false,defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "4",value = "pageSize") Integer pageSize,
                             String stuNumber, HttpServletRequest request, Model model){
        logger.info("跳转到咨询者主页,patient->{}",stuNumber);
        Patient patient = patientService.getStuByStuNumber(stuNumber);
        List<Document> documents = resourcesService.getAllDocument();
        request.getSession().setAttribute("userid",Integer.valueOf(patient.getId()));
        request.getSession().setAttribute("patient",patient);
        request.getSession().setAttribute("documents",documents);

        patientService.updatePatientState(true,patient);
        patient = patientService.getStuByStuNumber(stuNumber);

        //个人信息注册到redis中
        try{
            if(!userRedisService.isStuExist(patient)){
                userRedisService.insertStu(patient);
            }
        }catch (Exception e){
            logger.error("咨询者个人信息存入Redis失败");
        }

        PageInfo<Doctor> doctorPageInfo = doctorService.getDoctorPage(pageNum,pageSize);
        logger.info("首页心理医生列表分页->{}",JSON.toJSON(doctorPageInfo));

        model.addAttribute("doctorPageInfo",doctorPageInfo);
        return "stu/home";
    }

    /**
     * 咨询者注销登录
     * @param id
     * @param request
     * @return
     */
    @GetMapping(value = "/stu/return")
    public String returnPage(Integer id,HttpServletRequest request){
        logger.info("删除redis中咨询者在线状态id为->{}",id);
        Patient stu = patientService.getStuById(id);
        Boolean state = patientService.updatePatientState(false,stu);
        if (state){
            request.getSession().setAttribute("patient",null);
            logger.info("咨询者注销成功！");
            return "redirect:/stu";
        }else{
            logger.error("修改咨询者状态失败！");
            return "/error";
        }
    }

    /**
     * 跳转到我的问题页面
     * @return
     */
    @GetMapping(value = "/stu/toQuesPage")
    public String toQuesPage(HttpServletRequest request){
        Patient patient = (Patient) request.getSession().getAttribute("patient");
        logger.info("patient->{}",JSON.toJSON(patient));

        List<Question> questions = questionService.getQuestionByStu(patient);
        request.setAttribute("questions",questions);
        logger.info("根据stuId查询出的question->{}",JSON.toJSON(questions));
        return "/stu/question";
    }

    /**
     * 提交问题
     * @param question
     * @param request
     * @return
     */
    @PostMapping(value = "/stu/question")
    public String submitQuestion(Question question, HttpServletRequest request){
        logger.info("提交的问题->{}",JSON.toJSON(question));
        Patient patient = (Patient) request.getSession().getAttribute("patient");
        try{
            questionService.insertQues(question,patient);
        }catch (Exception e){
            logger.error("插入信息失败！");
            return "redirect:/error";
        }
        request.setAttribute("success","success");
        return "redirect:/stu/toQuesPage";
    }

    /**
     * 跳转到问答社区
     * @param request
     * @return
     */
    @GetMapping(value = "/stu/toAnsHood")
    public String toAnsHood(HttpServletRequest request){
        Patient patient = (Patient) request.getSession().getAttribute("patient");
        System.out.println("toAnsHood " + patient);
        //问题日期
        List<String> dates = questionService. getDates();
        request.setAttribute("dates",dates);

        //标签
        List<Tag> tags = questionService.getTags();
        request.setAttribute("tags",tags);

        return "/stu/answerHood";
    }

    /**
     * 获取对应标签的已回答的问题
     * @param tagId
     * @return
     */
    @GetMapping("/stu/getQuesByTag")
    @ResponseBody
    public List<Question> getQuesByTag(@RequestParam(value="tag") Integer tagId){
        return questionService.getQuestionByTagId(tagId);
    }

    /**
     *跳转到浏览问题详页
     * @param question
     * @return
     */
    @GetMapping(value = "/stu/toViewPage")
    public ModelAndView toAnsPage(Question question){
        logger.info("开始跳转到浏览问题详页");

        ModelAndView modelAndView = new ModelAndView();
        question = questionService.getQuestionByIdV2(question.getId());
        //添加浏览数
        questionService.insertViewCount(question.getId());
        logger.info("question的id->{},查出的question->{}",question.getId(),JSON.toJSON(question));
        String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(question.getGmtCreate()));

        List<AskAndAnswer> answers = question.getAskAndAnsList();

        for (AskAndAnswer answer :answers){
            if (answer.getGmtModified() != null)
                answer.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(answer.getGmtModified())));
        }

        modelAndView.addObject("updateTime",createTime);
        modelAndView.addObject("question",question);
        modelAndView.addObject("patient",question.getPatient());

        modelAndView.addObject("askAndAns_s",answers);
        modelAndView.addObject("questionAndTags",question.getQuestionAndTags());
        modelAndView.setViewName("/stu/viewQuestion");

        return modelAndView;
    }

    /**
     * 获取对应日期的已回答的问题
     * @param dateTime
     * @return
     */
    @GetMapping("/stu/getQuesByDate")
    @ResponseBody
    public List<Question> getQuesByDate(String dateTime){

        List<Question> questionByDate = questionService.getQuestionByDate(dateTime);
        logger.info("通过date->{}查询出的question->{}",dateTime,JSON.toJSON(questionByDate));
        return questionByDate;
    }

    /**
     * 跳转到活动页面
     * @return
     */
    @GetMapping(value = "/stu/toActivities")
    public String toActivityPage() {
        return "/stu/activities";
    }

    /**
     * 跳转到在线聊天系统
     * @param request
     * @return
     */
    @GetMapping(value = "/stu/toChatPage")
    public String toChatPage(HttpServletRequest request){
        Patient patient = (Patient) request.getSession().getAttribute("patient");

        //获取聊天心理医生id 和 图像编号
        String doctorId = request.getParameter("doctorId");
        Doctor doctor = doctorService.getDoctorById(Integer.valueOf(doctorId));
        request.setAttribute("doctor",doctor);
        request.setAttribute("iconNum",request.getParameter("iconNum"));

        //得到咨询者和所有心理医生的聊天关系
        List<Integer> chatFriendsId = chatFriendsService.getFriendsId(patient.getId());
        //判断该心理医生是否和咨询者已经建立聊天关系
        for (Integer id :chatFriendsId) {
            if(id.equals(Integer.valueOf(doctorId))){
                return "/chat/chats";
            }
        }
        //建立咨询者和心理医生之间的聊天关系
        chatFriendsService.setChatFriends(patient.getId(),Integer.valueOf(doctorId));

        return "/chat/chats";
    }

    @GetMapping(value = "/stu/toAppointment")
    public String toAppoint(Integer doctorId, Integer stuId,HttpServletRequest request){
        Doctor doctor = doctorService.getDoctorById(doctorId);
        Date tomorrow = new Date(System.currentTimeMillis() + 86399000);
        Date tomorrow1 = new Date(System.currentTimeMillis() + 172798000);
        Date tomorrow2 = new Date(System.currentTimeMillis() + 259197000);
        List<String> dates = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date1 = sdf.format(tomorrow);
        dates.add(date1);
        String date2 = sdf.format(tomorrow1);
        dates.add(date2);
        String date3 = sdf.format(tomorrow2);
        dates.add(date3);

        String[] time = {"8:00-10:00","10:00-12:00","14:00-16:00","16:00-18:00"};
        List<String> times = new ArrayList<>(Arrays.asList(time));

        request.setAttribute("doctor",doctor);
        request.setAttribute("stuId",stuId);
        request.setAttribute("dates",dates);
        request.setAttribute("times",times);

        return "/stu/appointment";
    }

    @PostMapping(value = "/stu/postAppointment")
    public String postAppointment(HttpServletRequest request){
        String patientId = request.getParameter("patientId");
        if(StringUtils.isEmpty(patientId))
            throw new CMSException(ResultCodeEnum.PARAM_ERROR.getCode(), "咨询者不能为空");
        String doctorId = request.getParameter("doctorId");
        if(StringUtils.isEmpty(patientId))
            throw new CMSException(ResultCodeEnum.PARAM_ERROR.getCode(), "医生不能为空");
        String date = request.getParameter("dates2");
        if(StringUtils.isEmpty(patientId))
            throw new CMSException(ResultCodeEnum.PARAM_ERROR.getCode(), "日期不能为空");
        String time = request.getParameter("times2");
        if(StringUtils.isEmpty(patientId))
            throw new CMSException(ResultCodeEnum.PARAM_ERROR.getCode(), "时间不能为空");
        String content = request.getParameter("content");
        if(StringUtils.isEmpty(patientId))
            throw new CMSException(ResultCodeEnum.PARAM_ERROR.getCode(), "咨询内容不能为空");
        Appointment appointment = new Appointment();
        appointment.setStuId(Integer.valueOf(patientId));
        appointment.setDoctorId(Integer.valueOf(doctorId));
        appointment.setDates(date);
        appointment.setTimes(time);
        appointment.setState(0);
        appointment.setContent(content);
        appointment.setGmtCreate(System.currentTimeMillis());
        try{
            patientService.insertAppointment(appointment);
        }catch (Exception e){
            logger.info("插入预约表失败！", e);
            throw new RuntimeException("预约失败");
        }
        return "200";
    }

    @GetMapping(value = "/stu/toMyAppointment")
    public String toMyAppointment(@RequestParam(required = false,defaultValue = "1") Integer pageNum,
                                  @RequestParam(defaultValue = "5",value = "pageSize") Integer pageSize,Model model,HttpServletRequest request){
        Patient patient = (Patient) request.getSession().getAttribute("patient");
        logger.info("patient->{}",JSON.toJSON(patient));

        PageInfo<Appointment> myAppointments = patientService.getMyAppointment(pageNum, pageSize,patient.getId());
        model.addAttribute("myAppointments",myAppointments);
        return "/stu/myAppointment";
    }

    @GetMapping(value = "/stu/removeAppointment")
    public String removeAppointment(Integer appointmentId){
        patientService.removeAppointmentByAppId(appointmentId);
        return "redirect:/stu/toMyAppointment";
    }

    @GetMapping(value = "/stu/toPsychoTest")
    public String toPsychoTest(){
        return "study/psychologicalTest";
    }

    @GetMapping(value = "/stu/toPsychoTestV2")
    public String toPsychoTest(Integer id){
        String result = "study/result"+id;
        return result;
    }

    @PostMapping(value = "/stu/compute")
    @ResponseBody
    public Integer compute(@RequestBody JSONArray data){
        logger.info("分数数组->{}",data);
        List<Object> list = JSONArray.parseArray(data.toJSONString());
        Integer sc = 0;
        for (Object score: list){
            Integer sco= Integer.valueOf((String)score);
            sc += sco;
        }
        Integer message=0;
        if (sc<=10){
            message=4;
        }else if (sc <= 30){
            message=3;
        }else if (sc <= 50){
            message=2;
        }else if (sc <= 70){
            message=1;
        }
        return message;

    }

}
