package ex03;

// B 개발자가 만든 것
public class UserController {

    @RequestMapping("/userinfo")
    public void userinfo(@Principle SessionUser sessionUser) {
        System.out.println(sessionUser.getId());
        System.out.println(sessionUser.getUsername());
        System.out.println("userinfo 호출됨");
    }

    @RequestMapping("/login")
    public void login(Model model) {
        System.out.println(model.getAttribute("username"));
        System.out.println("login 호출됨");
    }

    @RequestMapping("/join")
    public void join() {
        System.out.println("join 호출됨");
    }

    @RequestMapping("/logout")
    public void logout() {
        System.out.println("logout 호출됨");
    }
}
