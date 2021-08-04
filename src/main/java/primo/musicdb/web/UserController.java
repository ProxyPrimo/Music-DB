package primo.musicdb.web;

import org.modelmapper.ModelMapper;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import primo.musicdb.model.binding.UserRegistrationBindingModel;
import primo.musicdb.model.service.UserRegistrationServiceModel;
import primo.musicdb.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private final ModelMapper modelMapper;
    private final UserService userService;

    public UserController(ModelMapper modelMapper, UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @ModelAttribute("userRegistrationBindingModel")
    private UserRegistrationBindingModel createBindingModel() {
        return new UserRegistrationBindingModel();
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {

        return "register";
    }

    @PostMapping("/register")
    public String registerAndLoginUser(
            @Valid UserRegistrationBindingModel userRegistrationBindingModel
            , BindingResult bindingResult
            , RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("userRegistrationBindingModel", userRegistrationBindingModel);
            redirectAttributes
                    .addFlashAttribute(
                            "org.springframework.validation.BindingResult.userRegistrationBindingModel"
                    , bindingResult);

            return "redirect:/users/register";
        }

        if (userService.userNameExists(userRegistrationBindingModel.getUsername())) {
            redirectAttributes
                    .addFlashAttribute("userRegistrationBindingModel", userRegistrationBindingModel);

            redirectAttributes
                    .addFlashAttribute("userExistsError", true);

            return "redirect:/users/register";
        }

        if (userService.emailExists(userRegistrationBindingModel.getEmail())) {
            redirectAttributes
                    .addFlashAttribute("userRegistrationBindingModel", userRegistrationBindingModel);

            redirectAttributes
                    .addFlashAttribute("emailExistsError", true);

            return "redirect:/users/register";
        }

        UserRegistrationServiceModel userServiceModel = modelMapper
                .map(userRegistrationBindingModel
                        , UserRegistrationServiceModel.class);

        userService.registerAndLoginUser(userServiceModel);

        return "redirect:/home";
    }

    @PostMapping("/login-error")
    public ModelAndView failedLogin(
            @ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                    String username
    ) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("bad_credentials", true);
        modelAndView.addObject("username", username);

        modelAndView.setViewName("/login");

        return modelAndView;
    }
}
