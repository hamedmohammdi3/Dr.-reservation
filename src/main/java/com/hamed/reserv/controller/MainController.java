package com.hamed.reserv.controller;

import com.hamed.reserv.model.Person;
import com.hamed.reserv.model.ReservTimeClient;
import com.hamed.reserv.model.TimeSet;
import com.hamed.reserv.service.PersonRepository;
import com.hamed.reserv.service.ReservTimeClientRepository;
import com.hamed.reserv.service.TimeSetRepository;
import com.hamed.reserv.util.FileUploadUtil;
import com.hamed.reserv.util.TimeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author H.Mohammadi
 * @created 2022/06/09
 */
@Controller
public class MainController {

    @Autowired
    PersonRepository personRepository;
    @Autowired
    TimeSetRepository timeSetRepository;
    @Autowired
    ReservTimeClientRepository reservTimeClientRe;

    public static boolean isNumeric(String string) {
        int intValue;

        if (string == null || string.equals("")) {
            return false;
        }
        try {
            intValue = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
        }
        return false;
    }

    @GetMapping(value = "/login")
    public String login() {
        return "/pages/login";
    }

    @GetMapping(value = "/search")
    public String search(Model model) {
        model.addAttribute("persons", personRepository.findByIsDoctor(true));
        return "/pages/search";
    }

    @RequestMapping(value = "/api/profile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TimeSet> profile(@RequestBody TimeSet timeSet) {
        try {
            List<TimeSet> timeSetList = timeSetRepository.findByTimeAndPersonId(timeSet.getTime(), timeSet.getPersonId());
            TimeSet timeSet1 = null;
            if (timeSetList.size() > 0) {
                timeSet1 = timeSetList.get(0);
                timeSet.setId(timeSet1.getId());
                timeSet = reservTime(timeSet1, timeSet);
            }
            timeSetRepository.save(timeSet);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(timeSet);
    }

    @GetMapping(value = "/signup")
    public String signup() {
        return "/pages/signup";
    }

    @GetMapping(value = "/profile")
    public String profile(@RequestParam(value = "id", required = false) Long id, Model model) {
        if (id != null) {
            model.addAttribute("person", personRepository.findById(id).get());
        }
        return "/pages/profile";
    }

    @PostMapping(value = "/loginPerson")
    public String loginPerson(@RequestParam(value = "email", required = false) String email,
                              @RequestParam(value = "password", required = false) String password,
                              HttpSession session,
                              RedirectAttributes attributes) {
        if (email.isEmpty() || password.isEmpty()) {
            attributes.addFlashAttribute("success_created", "لطفا اطلاعات را به صورت کامل وارد کنید");
            attributes.addFlashAttribute("success_class", "alert alert-danger");
            return "redirect:/login";
        }
        List<Person> personList = personRepository.findByEmailAndPassword(email, password);
        if (personList != null && personList.size() > 0) {
            session.setAttribute("client", personList.get(0));
            return "redirect:/search";
        } else {
            attributes.addFlashAttribute("success_created", "اطلاعات وارد شده صحیح نمی باشد");
            attributes.addFlashAttribute("success_class", "alert alert-danger");
            return "redirect:/login";
        }
    }

    @PostMapping(value = "/setClient")
    public String setClient(@ModelAttribute Person person, HttpSession model, RedirectAttributes attributes) {
        if (person.getEmail() != null) {
            if (personRepository.findByEmail(person.getEmail()).size() > 0) {
                attributes.addFlashAttribute("success_created", "ایمیل وارد شده تکراری است");
                attributes.addFlashAttribute("success_class", "alert alert-danger");
                return "redirect:/signup";
            }
        } else {
            attributes.addFlashAttribute("success_created", "ایمیل درست نیست ");
            attributes.addFlashAttribute("success_class", "alert alert-danger");
            return "redirect:/signup";
        }
        Person person1 = personRepository.save(person);
        model.setAttribute("client", person1);
        return "/pages/signupClient";
    }

    @PostMapping("/setPerson")
    public String greetingSubmit(@RequestParam("image") MultipartFile multipartFile
            , @ModelAttribute Person person, HttpSession httpSession, RedirectAttributes attributes) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        if (person.getEmail() != null) {
            if (personRepository.findByEmail(person.getEmail()).size() > 0) {
                attributes.addFlashAttribute("success_created", "ایمیل وارد شده تکراری است");
                attributes.addFlashAttribute("success_class", "alert alert-danger");
                return "redirect:/signup";
            }
        } else {
            attributes.addFlashAttribute("success_created", "ایمیل درست نیست ");
            attributes.addFlashAttribute("success_class", "alert alert-danger");
            return "redirect:/signup";
        }

        person.setPhotos(fileName);
        person.setDoctor(person.getPCode() != null);
        Person person1 = personRepository.save(person);
        //String uploadDir = "user-photos/" + person1.getId();
        String uploadDir = "src/main/resources/static/images/profile";
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        httpSession.setAttribute("client", person1);
        attributes.addFlashAttribute("success_created", "ثبت نام انجام شد");
        attributes.addFlashAttribute("success_class", "alert alert-success");
        return "redirect:/signup";
    }

    @RequestMapping(value = "/api/timeReserv", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TimeSet> timeReserv(@RequestBody TimeSet timeSet) {
        TimeSet timeSet1 = null;
        try {
            List<TimeSet> timeSetList = timeSetRepository.findByTimeAndPersonId(timeSet.getTime(), timeSet.getPersonId());
            timeSet1 = timeSetList.get(0);
        } catch (Exception e) {
            return ResponseEntity.ok(new TimeSet());
        }
        return ResponseEntity.ok(timeSet1);
    }

    @GetMapping(value = "/signupClient")
    public String signupClient() {
        return "/pages/signupClient";
    }

    @GetMapping(value = "/cleanSession")
    public String cleanSession(HttpSession session) {
        session.removeAttribute("client");
        return "/index";
    }

    @GetMapping(value = "/reserv")
    public String reserv(@RequestParam(value = "id", required = false) Long id, Model model) {
        if (id != null) {
            model.addAttribute("person", personRepository.findById(id).get());
        }
        return "/pages/reserv";
    }

    @RequestMapping(value = "/api/reserv", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TimeSet> reserv(@RequestBody TimeSet timeSet, HttpSession session) {
        try {
            List<TimeSet> timeSetList = timeSetRepository.findByTimeAndPersonId(timeSet.getTime(), timeSet.getPersonId());
            TimeSet timeSet1 = reservTime(timeSet, timeSetList.get(0));
            timeSetRepository.save(timeSet1);
            insertInReservTimeClient(timeSet1, (Person) session.getAttribute("client"));
        } catch (Exception e) {
            return ResponseEntity.ok(new TimeSet());
        }
        return ResponseEntity.ok(timeSet);
    }

    @GetMapping(value = "/profileClient")
    public String profileClient(@RequestParam(value = "id", required = false) Long id, Model model) {
        List<ReservTimeClient> reservTimeClientList = reservTimeClientRe.findByUserId(id + "");
        for (ReservTimeClient reservTimeClient : reservTimeClientList) {
            reservTimeClient.setPerson(personRepository.findById(Long.parseLong(reservTimeClient.getDoctorId())).get());
        }
        if (id != null) {
            model.addAttribute("ReservTimeClients", reservTimeClientList);
        }
        return "/pages/profileClient";
    }

    @GetMapping(value = "/removeReserv")
    public String removeReserv(@RequestParam(value = "id", required = false) Long id, Model model, RedirectAttributes attributes) {
        try {
            ReservTimeClient reservTimeClient = reservTimeClientRe.findById(id).get();
            TimeSet timeSet = timeSetRepository.findById(reservTimeClient.getTimeSetId()).get();
            removeInReservTimeClient(timeSet, reservTimeClient.getUserId());
            reservTimeClientRe.deleteById(id);
            attributes.addAttribute("id", reservTimeClient.getUserId());

        } catch (Exception e) {
            attributes.addFlashAttribute("success_created", "خطا در حذف رزرو");
            attributes.addFlashAttribute("success_class", "alert alert-danger");

            return "redirect:/profileClient";
        }
        attributes.addFlashAttribute("success_created", "حذف رزرو انجام شد");
        attributes.addFlashAttribute("success_class", "alert alert-success");
        return "redirect:/profileClient";
    }

    @GetMapping(value = "/removeReservDoctor")
    public String removeReservDoctor(@RequestParam(value = "id", required = false) Long id,HttpSession session, RedirectAttributes attributes) {
        try {
            ReservTimeClient reservTimeClient = reservTimeClientRe.findById(id).get();
            TimeSet timeSet = timeSetRepository.findById(reservTimeClient.getTimeSetId()).get();
            removeInReservTimeClient(timeSet, reservTimeClient.getUserId());
            reservTimeClientRe.deleteById(id);
            attributes.addAttribute("id", ((Person) session.getAttribute( "client")).getId());

        } catch (Exception e) {
            attributes.addFlashAttribute("success_created", "خطا در حذف رزرو");
            attributes.addFlashAttribute("success_class", "alert alert-danger");

            return "redirect:/reservDoctor";
        }
        attributes.addFlashAttribute("success_created", "حذف رزرو انجام شد");
        attributes.addFlashAttribute("success_class", "alert alert-success");
        return "redirect:/reservDoctor";
    }

    @GetMapping(value = "/reservDoctor")
    public String reservDoctor(@RequestParam(value = "id", required = false) Long id, Model model) {

        if (id != null) {
            List<ReservTimeClient> reservTimeClientList = reservTimeClientRe.findByDoctorId(id + "");
            model.addAttribute("ReservTimeClients", reservTimeClientList);
        }

        return "/pages/reservDoctor";
    }

    @PostMapping("/search")
    public String greetingSubmit(@ModelAttribute Person person, Model model) {
        List<Person> personList = personRepository.findPersonByNameFamily(person.getName_family(),true);
        personList = personList.stream().filter(a-> a.getDoctor() != null && a.getDoctor()).collect(Collectors.toList());
        model.addAttribute("persons", personList);
        model.addAttribute("search", person.getName_family());

        return "/pages/search";
    }

    private void removeInReservTimeClient(TimeSet timeSet1, String clientId) {
        timeSet1.setC11(timeSet1.getC11().equals(clientId) ? "true" : timeSet1.getC11());
        timeSet1.setC12(timeSet1.getC12().equals(clientId) ? "true" : timeSet1.getC12());
        timeSet1.setC13(timeSet1.getC13().equals(clientId) ? "true" : timeSet1.getC13());
        timeSet1.setC14(timeSet1.getC14().equals(clientId) ? "true" : timeSet1.getC14());
        timeSet1.setC15(timeSet1.getC15().equals(clientId) ? "true" : timeSet1.getC15());
        timeSet1.setC16(timeSet1.getC16().equals(clientId) ? "true" : timeSet1.getC16());
        timeSet1.setC17(timeSet1.getC17().equals(clientId) ? "true" : timeSet1.getC17());
        timeSet1.setC18(timeSet1.getC18().equals(clientId) ? "true" : timeSet1.getC18());
        timeSet1.setC19(timeSet1.getC19().equals(clientId) ? "true" : timeSet1.getC19());
        timeSet1.setC21(timeSet1.getC21().equals(clientId) ? "true" : timeSet1.getC21());
        timeSet1.setC22(timeSet1.getC22().equals(clientId) ? "true" : timeSet1.getC22());
        timeSet1.setC23(timeSet1.getC23().equals(clientId) ? "true" : timeSet1.getC23());
        timeSet1.setC24(timeSet1.getC24().equals(clientId) ? "true" : timeSet1.getC24());
        timeSet1.setC25(timeSet1.getC25().equals(clientId) ? "true" : timeSet1.getC25());
        timeSet1.setC26(timeSet1.getC26().equals(clientId) ? "true" : timeSet1.getC26());
        timeSet1.setC27(timeSet1.getC27().equals(clientId) ? "true" : timeSet1.getC27());
        timeSet1.setC28(timeSet1.getC28().equals(clientId) ? "true" : timeSet1.getC28());
        timeSet1.setC29(timeSet1.getC29().equals(clientId) ? "true" : timeSet1.getC29());
        timeSetRepository.save(timeSet1);
    }

    private void insertInReservTimeClient(TimeSet timeSet1, Person client) {
        ReservTimeClient reservTimeClient = new ReservTimeClient();
        reservTimeClient.setDoctorId(timeSet1.getPersonId());
        reservTimeClient.setUserId(client.getId() + "");
        reservTimeClient.setDateSet(timeSet1.getTime());
        reservTimeClient.setTimeSetId(timeSet1.getId());
        String time = "";

        time = timeSet1.getC11().equals(client.getId() + "") ? TimeEnum.c11.value() : time;
        time = timeSet1.getC12().equals(client.getId() + "") ? time.equals("") ? TimeEnum.c12.value() : time + " و " + TimeEnum.c12.value() : time;
        time = timeSet1.getC13().equals(client.getId() + "") ? time.equals("") ? TimeEnum.c13.value() : time + " و " + TimeEnum.c13.value() : time;
        time = timeSet1.getC14().equals(client.getId() + "") ? time.equals("") ? TimeEnum.c14.value() : time + " و " + TimeEnum.c14.value() : time;
        time = timeSet1.getC15().equals(client.getId() + "") ? time.equals("") ? TimeEnum.c15.value() : time + " و " + TimeEnum.c15.value() : time;
        time = timeSet1.getC16().equals(client.getId() + "") ? time.equals("") ? TimeEnum.c16.value() : time + " و " + TimeEnum.c16.value() : time;
        time = timeSet1.getC17().equals(client.getId() + "") ? time.equals("") ? TimeEnum.c17.value() : time + " و " + TimeEnum.c17.value() : time;
        time = timeSet1.getC18().equals(client.getId() + "") ? time.equals("") ? TimeEnum.c18.value() : time + " و " + TimeEnum.c18.value() : time;
        time = timeSet1.getC19().equals(client.getId() + "") ? time.equals("") ? TimeEnum.c19.value() : time + " و " + TimeEnum.c19.value() : time;
        time = timeSet1.getC21().equals(client.getId() + "") ? time.equals("") ? TimeEnum.c21.value() : time + " و " + TimeEnum.c21.value() : time;
        time = timeSet1.getC22().equals(client.getId() + "") ? time.equals("") ? TimeEnum.c22.value() : time + " و " + TimeEnum.c22.value() : time;
        time = timeSet1.getC23().equals(client.getId() + "") ? time.equals("") ? TimeEnum.c23.value() : time + " و " + TimeEnum.c23.value() : time;
        time = timeSet1.getC24().equals(client.getId() + "") ? time.equals("") ? TimeEnum.c24.value() : time + " و " + TimeEnum.c24.value() : time;
        time = timeSet1.getC25().equals(client.getId() + "") ? time.equals("") ? TimeEnum.c25.value() : time + " و " + TimeEnum.c25.value() : time;
        time = timeSet1.getC26().equals(client.getId() + "") ? time.equals("") ? TimeEnum.c26.value() : time + " و " + TimeEnum.c26.value() : time;
        time = timeSet1.getC27().equals(client.getId() + "") ? time.equals("") ? TimeEnum.c27.value() : time + " و " + TimeEnum.c27.value() : time;
        time = timeSet1.getC28().equals(client.getId() + "") ? time.equals("") ? TimeEnum.c28.value() : time + " و " + TimeEnum.c28.value() : time;
        time = timeSet1.getC29().equals(client.getId() + "") ? time.equals("") ? TimeEnum.c29.value() : time + " و " + TimeEnum.c29.value() : time;

        if (!time.equals("")) {
            reservTimeClient.setTime(time);
            reservTimeClientRe.save(reservTimeClient);
        }
    }

    public TimeSet reservTime(TimeSet includeTimeSet, TimeSet baseTimeSet) {
        baseTimeSet.setC11(isNumeric(includeTimeSet.getC11()) ? includeTimeSet.getC11() : baseTimeSet.getC11());
        baseTimeSet.setC12(isNumeric(includeTimeSet.getC12()) ? includeTimeSet.getC12() : baseTimeSet.getC12());
        baseTimeSet.setC13(isNumeric(includeTimeSet.getC13()) ? includeTimeSet.getC13() : baseTimeSet.getC13());
        baseTimeSet.setC14(isNumeric(includeTimeSet.getC14()) ? includeTimeSet.getC14() : baseTimeSet.getC14());
        baseTimeSet.setC15(isNumeric(includeTimeSet.getC15()) ? includeTimeSet.getC15() : baseTimeSet.getC15());
        baseTimeSet.setC16(isNumeric(includeTimeSet.getC16()) ? includeTimeSet.getC16() : baseTimeSet.getC16());
        baseTimeSet.setC17(isNumeric(includeTimeSet.getC17()) ? includeTimeSet.getC17() : baseTimeSet.getC17());
        baseTimeSet.setC18(isNumeric(includeTimeSet.getC18()) ? includeTimeSet.getC18() : baseTimeSet.getC18());
        baseTimeSet.setC19(isNumeric(includeTimeSet.getC19()) ? includeTimeSet.getC19() : baseTimeSet.getC19());
        baseTimeSet.setC21(isNumeric(includeTimeSet.getC21()) ? includeTimeSet.getC21() : baseTimeSet.getC21());
        baseTimeSet.setC22(isNumeric(includeTimeSet.getC22()) ? includeTimeSet.getC22() : baseTimeSet.getC22());
        baseTimeSet.setC23(isNumeric(includeTimeSet.getC23()) ? includeTimeSet.getC23() : baseTimeSet.getC23());
        baseTimeSet.setC24(isNumeric(includeTimeSet.getC24()) ? includeTimeSet.getC24() : baseTimeSet.getC24());
        baseTimeSet.setC25(isNumeric(includeTimeSet.getC25()) ? includeTimeSet.getC25() : baseTimeSet.getC25());
        baseTimeSet.setC26(isNumeric(includeTimeSet.getC26()) ? includeTimeSet.getC26() : baseTimeSet.getC26());
        baseTimeSet.setC27(isNumeric(includeTimeSet.getC27()) ? includeTimeSet.getC27() : baseTimeSet.getC27());
        baseTimeSet.setC28(isNumeric(includeTimeSet.getC28()) ? includeTimeSet.getC28() : baseTimeSet.getC28());
        baseTimeSet.setC29(isNumeric(includeTimeSet.getC29()) ? includeTimeSet.getC29() : baseTimeSet.getC29());

        return baseTimeSet;
    }

}
