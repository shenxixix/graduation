//咨询者登录校验
var app = new Vue({
    el: "#sign_in",
    data: {
        stuNumber: "",
        password: "",
        content: "",
        patient: {},
        tel:"",
        code:"",
        isPass:true,
        endCodeDes: "获取验证码",
        endCodeStart:false,
        endStartNum: 60,
        timer:null
    },
    methods: {
        submitForm: async function () {

            this.patient = {}
            if(this.isPass) {
//                用户名或密码为空
                if (this.stuNumber.split(" ").join("").length === 0
                    || this.password.split(" ").join("").length === 0) {
                    this.content = "用户名或密码不能为空"
                    return false
                }
//           用户名或密码错误
                else {
                    _this = this;
                    try {
                        console.log(this.stuNumber);
                        console.log($.md5(this.password));
                        await axios.get("/psych/stu/stuChecked?stuNumber=" + this.stuNumber + "&password=" + $.md5(this.password)).then(res => {
                            //注意回调函数的this和vue的this会产生歧义
                            _this.patient = res.data;
                        })
                    } catch (err) {
                        console.log(err)
                    }
                    console.log(this.patient)
                    if (!($.isEmptyObject(this.patient))) {
                        this.content = ""
                        location.href = "/psych/stu/toHomePage?stuNumber=" + this.patient.stuNumber
                    } else {
                        this.content = "用户名或密码错误"
                    }
                }
            } else {
                //  验证码登录
                if (this.tel.split(" ").join("").length === 0
                    || this.code.split(" ").join("").length === 0) {
                    this.content = "电话号码或验证码不能为空"
                    return false
                }
                //           验证码登录
                else {
                    _this = this;
                    try {
                        console.log(this.tel);
                        console.log(this.code);
                        await axios.get("/psych/stu/stuAuthcode?tel=" + this.tel + "&authcode=" + this.code).then(res => {
                            //注意回调函数的this和vue的this会产生歧义
                            _this.patient = res.data;
                        })
                    } catch (err) {
                        console.log(err)
                    }
                    console.log(this.patient)
                    if (!($.isEmptyObject(this.patient)) && this.patient.stuNumber) {
                        this.content = ""
                        location.href = "/psych/stu/toHomePage?stuNumber=" + this.patient.stuNumber
                    } else {
                        this.content = "电话号码或验证码错误"
                    }
                }
            }
        },
        changeLoginMode: function (isPassword) {
            this.isPass = isPassword;
        },
        getAuthCode: function () {
            if(!this.endCodeStart) {
                this.endStartNum = 60;
                this.timer = setInterval(this.count, 1000);
            }
        },
        count: function () {
            if(this.endStartNum == 0) {
                this.endCodeDes = "获取验证码";
                clearInterval(this.timer);
                this.timer = null;
            } else {
                this.endStartNum = this.endStartNum - 1;
                this.endCodeDes = this.endStartNum;
            }
        }
    }
})

//咨询者注册
var app = new Vue({
    el: "#sign_up",
    data:{
        flag: 0,
        id: "",
        stuNumber: "",
        username: "",
        email: "",
        tel: "",
        age: "",
        password1: "",
        password2: "",
        text1: "",
        text2: "",
        text3: "",
        text4: "",
        text5: "",
        text6: "",
        text7: "",
        patient: {}
    },
    methods:{
        submitForm: async function () {

            this.flag = 0;
//                注意加this,表示vue与页面绑定的对象,以及如何判空
            if($.isEmptyObject(this.stuNumber)){
                this.text1 = "咨询者号不能为空"
            }else {
                this.flag++;
                this.text1 = "";
            }

            if($.isEmptyObject(this.username)){
                this.text2 = "用户名不能为空"
            }else{
                this.flag++;
                this.text2 = ""
            }

            if($.isEmptyObject(this.email)){
                this.text3 = "邮箱不能为空"
            }else{
                this.flag++;
                this.text3 = ""
            }

            if($.isEmptyObject(this.tel)){
                this.text4 = "电话不能为空"
            }else{
                this.flag++;
                this.text4 = ""
            }

            if($.isEmptyObject(this.age)){
                this.text5 = "年龄不能为空"
            }else{
                this.flag++;
                this.text5 = ""
            }

            if($.isEmptyObject(this.password1)){
                this.text6 = "密码不能为空"
            }else{
                this.flag++;
                this.text6 = ""
            }

            if(!(this.password1 === this.password2)){
                this.text7 = "密码输入不正确"
            }else{
                this.flag++
                this.text7=""
            }
            if(this.flag === 7){

                _this = this
                try{
                    await axios.get("/psych/stu/stuChecked1?stuNumber=" + this.stuNumber)
                        .then(res => {
                            _this.patient = res.data
                        })
                }catch(err){
                    console.log(err);
                    alert("请求出错")
                }

                console.log(this.patient)

                if(!$.isEmptyObject(this.patient)){
                    alert($.isEmptyObject(this.patient))
                    this.text1 = "该咨询者号已注册"
                }else{
                    // alert("注册成功")
//                      location.href="/mheal/stu/register?"
//                      post提交表单

                    let formdata = new FormData()
                    formdata.append("stuNumber",this.stuNumber)
                    formdata.append("name",this.username)
                    formdata.append("password",this.password1)
                    formdata.append("email",this.email)
                    formdata.append("tel",this.tel)
                    formdata.append("age",this.age)
                    for (var value of formdata.values()) {
                        console.log(value);
                    }

                    let config = {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        }
                    }

                    //异步提交表单，即使有返回页面，也不会实现页面跳转
                    axios.post('/psych/stu/register',formdata,config).then(res => {
                        alert("提交表单")
                    })

                    location.href="/psych/stu";
                }
            }
        }
    }
})
