package com.uniovi.sdi2324entrega1ext205;


import com.uniovi.sdi2324entrega1ext205.entities.Friendship;
import com.uniovi.sdi2324entrega1ext205.entities.Post;
import com.uniovi.sdi2324entrega1ext205.entities.User;
import com.uniovi.sdi2324entrega1ext205.pageobjects.*;
import com.uniovi.sdi2324entrega1ext205.repositories.FriendshipRepository;
import com.uniovi.sdi2324entrega1ext205.repositories.PostsRepository;
import com.uniovi.sdi2324entrega1ext205.repositories.UsersRepository;
import com.uniovi.sdi2324entrega1ext205.services.FriendshipService;
import com.uniovi.sdi2324entrega1ext205.services.RolesService;
import com.uniovi.sdi2324entrega1ext205.util.SeleniumUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ApplicationTests {

    //    @Value("${spring.data.web.pageable.size-parameter}")
//    int pageSize;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private FriendshipRepository friendshipRepository;
    @Autowired
    private RolesService rolesService;

    @Autowired
    private FriendshipService friendshipService;
    @Autowired
    private PostsRepository postsRepository;


    static String PathFirefox = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
    //static String Geckodriver = "C:\\Users\\adria\\Downloads\\PL-SDI-Sesión5-material\\geckodriver-v0.30.0-win64.exe";
    //static String Geckodriver ="";
    //static String Geckodriver ="F:\\Documentos\\Clase\\SDI\\PL-SDI-Sesión5-material\\geckodriver-v0.30.0-win64.exe";
    //static String Geckodriver = "C:\\Users\\adria\\Downloads\\PL-SDI-Sesión5-material\\geckodriver-v0.30.0-win64.exe";
    //static String Geckodriver = "C:\\Users\\carol\\Escritorio\\Clase\\tercero\\segundo cuatri\\SDI\\PL-SDI-Sesión5-material\\geckodriver-v0.30.0-win64.exe";
    static String Geckodriver = "F:\\uni\\2023-2024\\sdi\\projectos\\PL-SDI-Sesión5-material\\geckodriver-v0.30.0-win64.exe";
    @Value("${spring.data.web.pageable.size-parameter}")
    private int pageSize;
    static WebDriver driver = getDriver(PathFirefox, Geckodriver);
    static String URL = "http://localhost:8090";

    public static WebDriver getDriver(String PathFirefox, String Geckodriver) {
        System.setProperty("webdriver.firefox.bin", PathFirefox);
        System.setProperty("webdriver.gecko.driver", Geckodriver);
        driver = new FirefoxDriver();
        return driver;
    }

    @BeforeEach
    public void setUp() {
        driver.navigate().to(URL);
    }

    @AfterEach
    public void tearDown() {
        driver.manage().deleteAllCookies();
    }

    @BeforeAll
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
    static public void begin() {
    }

    @AfterAll
    static public void end() {
        driver.quit();
    }

    private static void navigateToPage(int page, String xpath) {
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", xpath);
        elements.get(page).click();
    }

    @Test
    @Order(1)
    void PR01() {
        //Vamos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        //Rellenamos el formulario.
        PO_SignUpView.fillForm(driver, "Pepe", "Pérez", "pp@gmail.com", "aA?123456789", "aA?123456789");
        //Comprobamos que entramos en la sección privada y nos nuestra el texto a buscar
        String checkText = "Usuarios";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    @Test
    @Order(2)
    void PR02()  {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        PO_SignUpView.fillForm(driver, "", "", "", "77777", "77777");


        List<WebElement> name = PO_SignUpView.checkElementByKey(driver, "Error.empty.name",
                PO_Properties.getSPANISH());
        List<WebElement> lastname = PO_SignUpView.checkElementByKey(driver, "Error.empty.lastName",
                PO_Properties.getSPANISH());
        List<WebElement> password = PO_SignUpView.checkElementByKey(driver, "Error.signup.password.robust",
                PO_Properties.getSPANISH());
        List<WebElement> email = PO_SignUpView.checkElementByKey(driver, "Error.empty.email",
                PO_Properties.getSPANISH());

        //Comprobar error nombre vacío
        String checkText = PO_HomeView.getP().getString("Error.empty.name", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText, name.get(0).getText());
        //Comprobar error apellido vacío

        checkText = PO_HomeView.getP().getString("Error.empty.lastName", PO_Properties.getSPANISH());

        Assertions.assertEquals(checkText, lastname.get(0).getText());
        //Comprobar error email vacío

        checkText = PO_HomeView.getP().getString("Error.empty.email", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText, email.get(0).getText().split("\\n")[0]);
        //Comprobar error password vacío

        checkText = PO_HomeView.getP().getString("Error.signup.password.robust", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText, password.get(0).getText().split("\\n")[0]);
    }

    @Test
    @Order(3)
    void PR03() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");

        PO_SignUpView.fillForm(driver, "Pepe", "Pérez", "pp@gmail.com", "aA?123456789", "aA?123456788");
        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.passwordConfirm.coincidence",
                PO_Properties.getSPANISH());
        String checkText = PO_HomeView.getP().getString("Error.signup.passwordConfirm.coincidence", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    @Test
    @Order(4)
    void PR04() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");

        PO_SignUpView.fillForm(driver, "Pepe", "Pérez", "user01@email.com", "aA?123456789", "aA?123456789");
        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.email.already.registered",
                PO_Properties.getSPANISH());
        String checkText = PO_HomeView.getP().getString("Error.email.already.registered", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    @Test
    @Order(5)
    public void PR05() {
        PO_LoginView.login(driver, "admin@email.com", "@Dm1n1str@D0r", "Usuarios");
    }

    @Test
    @Order(6)
    public void PR06() {
        PO_LoginView.login(driver, "user01@email.com", "Us3r@1-PASSW", "Usuarios");
    }

    @Test
    @Order(7)
    public void PR07() {
        PO_LoginView.login(driver, "", "", PO_HomeView.getP().getString("login.message", PO_Properties.getSPANISH()));
    }

    @Test
    @Order(8)
    public void PR08() {
        PO_LoginView.login(driver, "user01@email.com", "abcdefg", PO_HomeView.getP().getString("login.error.message", PO_Properties.getSPANISH()));
    }

    @Test
    @Order(9)
    void PR09() {
        PO_LoginView.login(driver, "user01@email.com", "Us3r@1-PASSW", "Usuarios");

        PO_NavView.clickOption(driver, "logout", "class", "btn btn-primary");

        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "login.logout.message",
                PO_Properties.getSPANISH());
        String checkText = PO_HomeView.getP().getString("login.logout.message", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText, result.get(0).getText());
    }


    @Test
    @Order(10)
    void PR10() {
        PO_LoginView.login(driver, "user01@email.com", "Us3r@1-PASSW", "Usuarios");
        PO_NavView.clickOption(driver, "logout", "class", "btn btn-primary");
        Assertions.assertThrows(TimeoutException.class, () -> PO_NavView.clickOption(driver, "logout", "class", "btn btn-primary"));
    }

    @Test
    @Order(11)
    public void PR11() {
        PO_LoginView.login(driver, "admin@email.com", "@Dm1n1str@D0r", "Usuarios");
        PO_View.checkElementByAndClick(driver, "id", "userDropdown");
        PO_View.checkElementByAndClick(driver, "id", "adminUserList");
        //"/html/body/div/div/form/table/tbody/tr[1]"
        //List<WebElement> elements = PO_View.checkElementBy(driver,"free","");
        List<User> userlist = new ArrayList<User>();
        usersRepository.findAll().forEach(userlist::add);
        int size = userlist.size();
//        for (int i=0 ; i<size%pageSize ; i++) {
//
//        }
//        int page =1;
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "/html/body/div/div/form/table/tbody/tr");
        if (PO_PrivateView.pageOptionChecked(driver, "//a[contains(@class, 'page-link')]", 1))
            elements.addAll(PO_View.checkElementBy(driver, "free", "/html/body/div/div/form/table/tbody/tr"));
        while (PO_PrivateView.pageOptionChecked(driver, "//a[contains(@class, 'page-link')]", 3)) {
            elements.addAll(PO_View.checkElementBy(driver, "free", "/html/body/div/div/form/table/tbody/tr"));
        }
        Assertions.assertEquals(size, elements.size());

    }

    @Test
    @Order(12)
    public void PR12() {
        PO_LoginView.login(driver, "admin@email.com", "@Dm1n1str@D0r", "Usuarios");
        PO_View.checkElementByAndClick(driver, "id", "userDropdown");
        PO_View.checkElementByAndClick(driver, "id", "adminUserList");
        User oldUser = PO_AdminUserList.getUserPos(driver, 2, "/html/body/div/div/form/table/tbody");

        PO_AdminUserList.getUserPos(driver, 2, "/html/body/div/div/form/table/tbody");
        PO_AdminUserList.clickModUserPos(driver, 2, "html/body/div/div/form/table/tbody");
        PO_AdminUserList.modUserForm(driver, "pepe2@email.com", "pepe2", "pepito"
                , rolesService.getRoles()[1]);

        PO_View.checkElementByAndClick(driver, "id", "userDropdown");
        PO_View.checkElementByAndClick(driver, "id", "adminUserList");
        User newUser = PO_AdminUserList.getUserPos(driver, 2, "/html/body/div/div/form/table/tbody");
        Assertions.assertEquals(newUser.getEmail(), "pepe2@email.com");
        Assertions.assertEquals(newUser.getName(), "pepe2");
        Assertions.assertEquals(newUser.getLastName(), "pepito");
        Assertions.assertEquals(newUser.getRole(), rolesService.getRoles()[1]);
        PO_PrivateView.logoutUser(driver, PO_Properties.getSPANISH());
        PO_LoginView.login(driver, "pepe2@email.com", "Us3r@1-PASSW", "Usuarios"); //Us3r@1-PASSW
        PO_View.checkElementByAndClick(driver, "id", "userDropdown");
        PO_View.checkElementByAndClick(driver, "id", "adminUserList");
        List<WebElement> e = PO_View.checkElementBy(driver, "text", "borrar");
        Assertions.assertTrue(e.size() > 0);


    }

    @Test
    @Order(13)
    public void PR13() {
        PO_LoginView.login(driver, "admin@email.com", "@Dm1n1str@D0r", "Usuarios");
        PO_View.checkElementByAndClick(driver, "id", "userDropdown");
        PO_View.checkElementByAndClick(driver, "id", "adminUserList");
        User oldUser = PO_AdminUserList.getUserPos(driver, 2, "/html/body/div/div/form/table/tbody");

        PO_AdminUserList.getUserPos(driver, 2, "/html/body/div/div/form/table/tbody");
        PO_AdminUserList.clickModUserPos(driver, 2, "html/body/div/div/form/table/tbody");
        PO_AdminUserList.modUserForm(driver, "test2@email.com", " ", " "
                , rolesService.getRoles()[1]);
        List<String> errorMsgList = new ArrayList<>();
        errorMsgList.add(PO_HomeView.getP().getString("Error.email.already.registered", PO_Properties.getSPANISH()));
        errorMsgList.add(PO_HomeView.getP().getString("Error.name.whitespaces", PO_Properties.getSPANISH()));
        errorMsgList.add(PO_HomeView.getP().getString("Error.lastName.whitespaces", PO_Properties.getSPANISH()));
        Assertions.assertTrue(errorMsgList.size() == 3 && errorMsgList.stream().noneMatch(s -> s.trim().isEmpty()));


        PO_View.checkElementByAndClick(driver, "id", "userDropdown");
        PO_View.checkElementByAndClick(driver, "id", "adminUserList");
        User newUser = PO_AdminUserList.getUserPos(driver, 2, "/html/body/div/div/form/table/tbody");
        Assertions.assertEquals(newUser.getEmail(), oldUser.getEmail());
        Assertions.assertEquals(newUser.getName(), oldUser.getName());
        Assertions.assertEquals(newUser.getLastName(), oldUser.getLastName());
        Assertions.assertEquals(newUser.getRole(), rolesService.getRoles()[0]);


    }

    @Test
    @Order(14)
    public void PR14() {
        PO_LoginView.login(driver, "admin@email.com", "@Dm1n1str@D0r", "Usuarios");
        PO_View.checkElementByAndClick(driver, "id", "userDropdown");
        PO_View.checkElementByAndClick(driver, "id", "adminUserList");
        User oldUser = PO_AdminUserList.getUserPos(driver, 2, "/html/body/div/div/form/table/tbody");
        PO_AdminUserList.deleteUserListPos(driver, Arrays.asList(2), "/html/body/div/div/form/table/tbody");
        PO_View.checkElementByAndClick(driver, "id", "userDropdown");
        PO_View.checkElementByAndClick(driver, "id", "adminUserList");
        User otherUser = PO_AdminUserList.getUserPos(driver, 2, "/html/body/div/div/form/table/tbody");
        Assertions.assertNotEquals(otherUser.getEmail(), oldUser.getEmail());
        Assertions.assertNotEquals(otherUser.getName(), oldUser.getName());
        Assertions.assertNotEquals(otherUser.getLastName(), oldUser.getLastName());


    }

    @Test
    @Order(15)
    public void PR15() {
        PO_LoginView.login(driver, "admin@email.com", "@Dm1n1str@D0r", "Usuarios");
        PO_View.checkElementByAndClick(driver, "id", "userDropdown");
        PO_View.checkElementByAndClick(driver, "id", "adminUserList");
        PO_PrivateView.pageOptionChecked(driver, "//a[contains(@class, 'page-link')]", 2);
        List<WebElement> elements = PO_View.checkElementBy(driver, "free"
                , "/html/body/div/div/form/table/tbody/tr");
        int last = elements.size(); //se coje el anterior al ultimo puesto que el ultimo es un admin
        User oldUser = PO_AdminUserList.getUserPos(driver, last, "/html/body/div/div/form/table/tbody");
        PO_AdminUserList.deleteUserListPos(driver, Arrays.asList(last), "/html/body/div/div/form/table/tbody");
        PO_View.checkElementByAndClick(driver, "id", "userDropdown");
        PO_View.checkElementByAndClick(driver, "id", "adminUserList");

        PO_PrivateView.pageOptionChecked(driver, "//a[contains(@class, 'page-link')]", 2);
        elements = PO_View.checkElementBy(driver, "free"
                , "/html/body/div/div/form/table/tbody/tr");
        last = elements.size();
        User otherUser = PO_AdminUserList.getUserPos(driver, last, "/html/body/div/div/form/table/tbody");
        Assertions.assertNotEquals(otherUser.getEmail(), oldUser.getEmail());
        Assertions.assertNotEquals(otherUser.getName(), oldUser.getName());
        Assertions.assertNotEquals(otherUser.getLastName(), oldUser.getLastName());


    }

    @Test
    @Order(16)
    public void PR16() {
        PO_LoginView.login(driver, "admin@email.com", "@Dm1n1str@D0r", "Usuarios");
        PO_View.checkElementByAndClick(driver, "id", "userDropdown");
        PO_View.checkElementByAndClick(driver, "id", "adminUserList");
        Integer[] ids = {2, 3, 4};
        List<User> oldUsers = new ArrayList<>();
        Arrays.stream(ids).forEach(id ->
                oldUsers.add(PO_AdminUserList.getUserPos(driver, id, "/html/body/div/div/form/table/tbody"))
        );
        PO_AdminUserList.deleteUserListPos(driver, Arrays.asList(ids), "/html/body/div/div/form/table/tbody");
        PO_View.checkElementByAndClick(driver, "id", "userDropdown");
        PO_View.checkElementByAndClick(driver, "id", "adminUserList");

        List<User> otherUsers = new ArrayList<>();
        Arrays.stream(ids).forEach(id ->
                otherUsers.add(PO_AdminUserList.getUserPos(driver, id, "/html/body/div/div/form/table/tbody"))
        );

        for (int i = 0; i < oldUsers.size(); i++) {
            User oldUser = oldUsers.get(i);
            User otherUser = otherUsers.get(i);

            Assertions.assertNotEquals(otherUser.getEmail(), oldUser.getEmail());
            Assertions.assertNotEquals(otherUser.getName(), oldUser.getName());
            Assertions.assertNotEquals(otherUser.getLastName(), oldUser.getLastName());
        }


    }

    @Test
    @Order(17) //TODO MEJORABLE solo compruebo el numero de usuarios no su contenido
    void PR17() {
        PO_LoginView.login(driver, "user01@email.com", "Us3r@1-PASSW","Usuarios");
        PO_NavView.clickOption(driver, "user/list", "class", "btn btn-primary");
        List<User> userlist = new ArrayList<User>();
        usersRepository.findAllByRoleNot(rolesService.getRoles()[1]).forEach(userlist::add);
        int size = userlist.size()-1; //-1 para quitar el usuario actual

        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "/html/body/div/div/form/table/tbody/tr");
        if (PO_PrivateView.pageOptionChecked(driver, "//a[contains(@class, 'page-link')]", 1))
            elements.addAll(PO_View.checkElementBy(driver, "free", "/html/body/div/div/form/table/tbody/tr"));
        while (PO_PrivateView.pageOptionChecked(driver, "//a[contains(@class, 'page-link')]", 3)) {
            elements.addAll(PO_View.checkElementBy(driver, "free", "/html/body/div/div/form/table/tbody/tr"));
        }
        Assertions.assertEquals(size, elements.size());

    }

    @Test
    @Order(18)
    void PR18() {

        PO_LoginView.login(driver, "user01@email.com", "Us3r@1-PASSW","Usuarios");
        PO_NavView.clickOption(driver, "user/list", "class", "btn btn-primary");

        PO_View.checkElementByAndClick(driver, "text", "Buscar");

        List<User> userlist = new ArrayList<User>();
        usersRepository.findAllByRoleNot(rolesService.getRoles()[1]).forEach(userlist::add);
        int size = userlist.size()-1; //-1 para quitar el usuario actual

        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "/html/body/div/div/form/table/tbody/tr");
        if (PO_PrivateView.pageOptionChecked(driver, "//a[contains(@class, 'page-link')]", 1))
            elements.addAll(PO_View.checkElementBy(driver, "free", "/html/body/div/div/form/table/tbody/tr"));
        while (PO_PrivateView.pageOptionChecked(driver, "//a[contains(@class, 'page-link')]", 3)) {
            elements.addAll(PO_View.checkElementBy(driver, "free", "/html/body/div/div/form/table/tbody/tr"));
        }
        Assertions.assertEquals(size, elements.size());




        //------
//        String email = "user01@email.com";
//        PO_LoginView.login(driver, email, "Us3r@1-PASSW","Usuarios");
//        PO_NavView.clickOption(driver, "user/list", "class", "btn btn-primary");
//
//        PO_View.checkElementByAndClick(driver, "text", "Buscar");
//
//        Assertions.assertThrows(TimeoutException.class, ()-> PO_View.checkElementBy(driver, "text", email));
//
//        PO_NavView.clickOption(driver, "?page=5", "class", "btn btn-primary");
//
//        Assertions.assertThrows(TimeoutException.class, ()-> PO_View.checkElementBy(driver, "text", "admin@email.com"));
//        Assertions.assertThrows(TimeoutException.class, ()-> PO_View.checkElementBy(driver, "text", "admin"));
//        Assertions.assertThrows(TimeoutException.class, ()-> PO_View.checkElementBy(driver, "text", "admin2@email.com"));
    }

    @Test
    @Order(19)
    void PR19() {
        String email = "user01@email.com";
        PO_LoginView.login(driver, email, "Us3r@1-PASSW","Usuarios");
        PO_NavView.clickOption(driver, "user/list", "class", "btn btn-primary");

        WebElement searchText = driver.findElement(By.name("searchText"));
        searchText.click();
        searchText.clear();
        searchText.sendKeys("amarillo");

        PO_View.checkElementByAndClick(driver, "text", "Buscar");
        Assertions.assertThrows(TimeoutException.class, ()->SeleniumUtils.waitLoadElementsBy(driver, "free", "//tbody/tr",
                PO_View.getTimeout()));
//        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "/html/body/div/div/form/table/tbody/tr");
//        Assertions.assertEquals(0, elements.size());

    }

    @Test
    @Order(20)
    void PR20() {
        String email = "user01@email.com";
        PO_LoginView.login(driver, email, "Us3r@1-PASSW","Usuarios");
        PO_NavView.clickOption(driver, "user/list", "class", "btn btn-primary");

        WebElement searchText = driver.findElement(By.name("searchText"));
        searchText.click();
        searchText.clear();
        searchText.sendKeys("user01");

        PO_View.checkElementByAndClick(driver, "text", "Buscar");
        List<WebElement> usersList = SeleniumUtils.waitLoadElementsBy(driver, "free", "//tbody/tr",
                PO_View.getTimeout());
        Assertions.assertEquals(1, usersList.size());

        PO_View.checkElementBy(driver, "text", "user01@email.com");
        PO_View.checkElementBy(driver, "text", "name01");
        PO_View.checkElementBy(driver, "text", "lastname01");

//        searchText = driver.findElement(By.name("searchText"));
//        searchText.click();
//        searchText.clear();
//        PO_View.checkElementByAndClick(driver, "text", "Buscar");

    }

    @Test
    @Order(21)//TODO revisar
    public void PR21(){
        PO_LoginView.login(driver, "user01@email.com", "Us3r@1-PASSW","Usuarios");
        //Acceder a la pantalla de usuarios
//        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//*[@id='myNavbar']/ul[1]/li[3]");
//        elements.get(0).click();
        PO_NavView.clickOption(driver, "user/list", "class", "btn btn-primary");

        //Enviar solicitud de amistad al usuario con correo test1@email.com
        By enlace = By.xpath("//td[contains(text(), 'user02@email.com')]/following-sibling::*[3]");
        driver.findElement(enlace).click();
        //Cerrar sesión
        PO_PrivateView.logoutUser(driver,  PO_Properties.getSPANISH());
        //Iniciar sesión con test1@email.com
        PO_LoginView.login(driver, "user02@email.com", "Us3r@2-PASSW","Usuarios");
        //Acceder a las solicitudes de amistad
        PO_View.checkElementByAndClick(driver, "id", "friendshipDropdown");
        PO_View.checkElementByAndClick(driver, "id", "friendshipRequestList");
//        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//*[@id='myNavbar']/ul[1]/li[4]");
//        elements.get(0).click();
//
//        elements = PO_View.checkElementBy(driver, "free", "//a[contains(@href, 'friendship/request/list')]");
//        elements.get(0).click();
        //Buscar test0@email.com entre las solicitudes
        enlace = By.xpath("//td[contains(text(), 'user01@email.com')]");
        //Comprobar que la solicitud se encuentra en la tabla
        Assertions.assertNotNull(driver.findElement(enlace));
    }

    @Test
    @Order(22)
    public void PR22(){
        PO_LoginView.login(driver, "user01@email.com", "Us3r@1-PASSW","Usuarios");
        PO_NavView.clickOption(driver, "user/list", "class", "btn btn-primary");

        By enlace = By.xpath("//td[contains(text(), 'user02@email.com')]/following-sibling::*[3]");
        driver.findElement(enlace).click();

        Assertions.assertEquals(  PO_HomeView.getP().getString("user.sent", PO_Properties.getSPANISH()),driver.findElement(enlace).getText());


    }
    @Test
    @Order(23)
    public void PR23(){
        PO_LoginView.login(driver, "user01@email.com", "Us3r@1-PASSW","Usuarios");
        PO_View.checkElementByAndClick(driver, "id", "friendshipDropdown");
        PO_View.checkElementByAndClick(driver, "id", "friendshipRequestList");
        User current = usersRepository.findByEmail("user01@email.com");

        List<Friendship> pendinglist = new ArrayList<Friendship>();
        friendshipRepository.findAllByReceiverAndState(current,friendshipService.states[1]).forEach(pendinglist::add);
        int size = pendinglist.size();

        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//tbody/tr");
        if (PO_PrivateView.pageOptionChecked(driver, "//a[contains(@class, 'page-link')]", 1))
            elements.addAll(PO_View.checkElementBy(driver, "free", "//tbody/tr"));
        while (PO_PrivateView.pageOptionChecked(driver, "//a[contains(@class, 'page-link')]", 3)) {
            elements.addAll(PO_View.checkElementBy(driver, "free", "//tbody/tr"));
        }
        Assertions.assertEquals(size, elements.size());

    }

    @Test
    @Order(24)
    void PR24(){
        PO_LoginView.login(driver, "user01@email.com", "Us3r@1-PASSW","Usuarios");
        PO_View.checkElementByAndClick(driver, "id", "friendshipDropdown");
        PO_View.checkElementByAndClick(driver, "id", "friendshipRequestList");
        By enlace = By.xpath("//td[contains(text(), 'user03@email.com')]/following-sibling::*[2]");
        driver.findElement(enlace).click();
        Assertions.assertThrows(NoSuchElementException.class, () ->{ driver.findElement(enlace);});
        PO_PrivateView.logoutUser(driver,  PO_Properties.getSPANISH());

    }

    @Test
    @Order(25)
    void PR25(){
        PO_LoginView.login(driver, "user01@email.com", "Us3r@1-PASSW","Usuarios");
        PO_View.checkElementByAndClick(driver, "id", "friendshipDropdown");
        PO_View.checkElementByAndClick(driver, "id", "friendshipList");
        User current = usersRepository.findByEmail("user01@email.com");

        List<Friendship> friendlist = new ArrayList<Friendship>();
        friendshipRepository.findAllByReceiverAndState(current,friendshipService.states[0]).forEach(friendlist::add);
        int size = friendlist.size();

        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//tbody/tr");

        if (PO_PrivateView.pageOptionChecked(driver, "//a[contains(@class, 'page-link')]", 1))
            elements.addAll(PO_View.checkElementBy(driver, "free", "//tbody/tr"));
        while (PO_PrivateView.pageOptionChecked(driver, "//a[contains(@class, 'page-link')]", 3)) {
            elements.addAll(PO_View.checkElementBy(driver, "free", "//tbody/tr"));
        }
        Assertions.assertEquals(size, elements.size());
//                elements = PO_View.checkElementBy(driver, "text", "ana@gmail.com");
//        Assertions.assertEquals("ana@gmail.com", elements.get(0).getText());

    }



    @Test
    @Order(26)
    void PR26(){
        PO_LoginView.login(driver, "user01@email.com", "Us3r@1-PASSW","Usuarios");
        PO_View.checkElementByAndClick(driver, "id", "friendshipDropdown");
        PO_View.checkElementByAndClick(driver, "id", "friendshipList");
        User current = usersRepository.findByEmail("user01@email.com");

        List<Friendship> friendlist = new ArrayList<Friendship>();
        friendshipRepository.findAllByReceiverAndState(current,friendshipService.states[0]).forEach(friendlist::add);
        int size = friendlist.size();
        String checkDesc = "description 19";
        String checkTitle = "Post 19";
        String checkDate = new Date(System.currentTimeMillis()).toString();
        List<WebElement> descripciones = PO_View.checkElementBy(driver, "text", checkDesc);
        List<WebElement> posts =PO_View.checkElementBy(driver, "text", checkTitle);
        List<WebElement> dates =PO_View.checkElementBy(driver, "text", checkDate);
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//tbody/tr");

        if (PO_PrivateView.pageOptionChecked(driver, "//a[contains(@class, 'page-link')]", 1)) {
            elements.addAll(PO_View.checkElementBy(driver, "free", "//tbody/tr"));
            descripciones.addAll(PO_View.checkElementBy(driver, "text", checkDesc));
            posts.addAll(PO_View.checkElementBy(driver, "text", checkTitle));
            dates.addAll(PO_View.checkElementBy(driver, "text", checkDate));
        }
        while (PO_PrivateView.pageOptionChecked(driver, "//a[contains(@class, 'page-link')]", 3)) {
            elements.addAll(PO_View.checkElementBy(driver, "free", "//tbody/tr"));
            descripciones.addAll(PO_View.checkElementBy(driver, "text", checkDesc));
            posts.addAll(PO_View.checkElementBy(driver, "text", checkTitle));
            dates.addAll(PO_View.checkElementBy(driver, "text", checkDate));
        }
        Assertions.assertEquals(size, elements.size());
        Assertions.assertEquals(size, descripciones.size());
        Assertions.assertEquals(size, posts.size());
        Assertions.assertEquals(size, dates.size());

    }
    @Test
    @Order(27)
    void PR27() {
        PO_LoginView.login(driver, "user01@email.com", "Us3r@1-PASSW","Usuarios");

        PO_View.checkElementByAndClick(driver, "id", "btnPost");
        PO_View.checkElementByAndClick(driver, "id", "btnPublicar");

        // Rellenamos el formulario para añadir la publicación
        String checkDesc = "Ejemplo description";
        String checkTitle = "Ejemplo title";
        PO_PostsView.fillFormAddPost(driver, checkTitle, checkDesc);

        // Comprobamos que aparezca el nuevo post
        PO_PrivateView.pageOptionChecked(driver, "//a[contains(@class, 'page-link')]", 2);
        List<WebElement> elements = PO_View.checkElementBy(driver, "text", checkTitle);
        Assertions.assertEquals(checkTitle, elements.get(0).getText());
    }
    @Test
    @Order(28)
    void PR28() {

        PO_LoginView.login(driver, "user01@email.com", "Us3r@1-PASSW","Usuarios");

        PO_View.checkElementByAndClick(driver, "id", "btnPost");
        PO_View.checkElementByAndClick(driver, "id", "btnPublicar");

        // Rellenamos el formulario con title empty
        String checkDesc = "Ejemplo description";
        String checkTitle = " ";
        PO_PostsView.fillFormAddPost(driver, checkTitle, checkDesc);

        // Comprobamos que ta mal
        List<WebElement> result = PO_View.checkElementByKey(driver, "Error.empty", PO_Properties.getSPANISH());
        String checkText = PO_View.getP().getString("Error.empty", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText , result.get(0).getText());

        // Lo rellenamos otra vez, pero con description empty
        checkDesc = " ";
        checkTitle = "Ejemplo title";
        PO_PostsView.fillFormAddPost(driver, checkTitle, checkDesc);

        // Comprobamos que vuelve a estar mal
        result = PO_View.checkElementByKey(driver, "Error.empty", PO_Properties.getSPANISH());
        checkText = PO_View.getP().getString("Error.empty", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText , result.get(0).getText());
    }
    @Test
    @Order(29)
    void PR29() {
        PO_LoginView.login(driver, "user01@email.com", "Us3r@1-PASSW","Usuarios");
        PO_View.checkElementByAndClick(driver, "id", "btnPost");
        PO_View.checkElementByAndClick(driver, "id", "btnMisPosts");
        User current = usersRepository.findByEmail("user01@email.com");

        List<Post> postList = new ArrayList<Post>();
        postsRepository.findAllByUser(current).forEach(postList::add);
        int size = postList.size();

        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//tbody/tr");

        if (PO_PrivateView.pageOptionChecked(driver, "//a[contains(@class, 'page-link')]", 1))
            elements.addAll(PO_View.checkElementBy(driver, "free", "//tbody/tr"));
        while (PO_PrivateView.pageOptionChecked(driver, "//a[contains(@class, 'page-link')]", 3)) {
            elements.addAll(PO_View.checkElementBy(driver, "free", "//tbody/tr"));
        }
        Assertions.assertEquals(size, elements.size());

    }
    @Test
    @Order(30)
    void PR30() {
        PO_LoginView.login(driver, "user01@email.com", "Us3r@1-PASSW","Usuarios");
        PO_View.checkElementByAndClick(driver, "id", "btnPost");
        PO_View.checkElementByAndClick(driver, "id", "btnMisPosts");
        User current = usersRepository.findByEmail("user01@email.com");
        List<Friendship> friendlist = new ArrayList<Friendship>();
        friendshipRepository.findAllByReceiverAndState(current,friendshipService.states[0]).forEach(friendlist::add);
        User friend = friendlist.get(0).getSender();
        driver.navigate().to(URL + "/user/"+friendlist.get(0).getSender().getId());

        List<WebElement> elements = PO_View.checkElementBy(driver, "text", friend.getEmail());
        Assertions.assertEquals(elements.get(0).getText(), friend.getEmail());
        elements = PO_View.checkElementBy(driver, "text", friend.getName());
        Assertions.assertEquals(elements.get(0).getText(), friend.getName());
        elements = PO_View.checkElementBy(driver, "text", friend.getLastName());
        Assertions.assertEquals(elements.get(0).getText(), friend.getLastName());

        List<Post> postList = new ArrayList<Post>();
        postsRepository.findAllByUser(friend).forEach(postList::add);
        int size = postList.size();

        elements = PO_View.checkElementBy(driver, "free", "//tbody/tr");

        if (PO_PrivateView.pageOptionChecked(driver, "//a[contains(@class, 'page-link')]", 1))
            elements.addAll(PO_View.checkElementBy(driver, "free", "//tbody/tr"));
        while (PO_PrivateView.pageOptionChecked(driver, "//a[contains(@class, 'page-link')]", 3)) {
            elements.addAll(PO_View.checkElementBy(driver, "free", "//tbody/tr"));
        }
        Assertions.assertEquals(size, elements.size());

    }
    @Test
    @Order(31)
    void PR31() {
        PO_LoginView.login(driver, "user01@email.com", "Us3r@1-PASSW","Usuarios");

        driver.navigate().to(URL + "/user/362");

        List<WebElement> elements = PO_View.checkElementBy(driver, "text"
                , "Forbidden");
    }

    private void checkLenguage(int lg){

        PO_NavView.changeLanguage(driver,"btn"+PO_View.getP().getIDIOMS()[lg].getDisplayName(Locale.UK));

        PO_LoginView.login(driver, "user01@email.com", "Us3r@1-PASSW"
                ,PO_NavView.getP().getString("user.list.title", lg));

        PO_View.checkElementBy(driver, "text", PO_NavView.getP().getString("user.list.search", lg));
        PO_View.checkElementBy(driver, "text", PO_NavView.getP().getString("user.sent", lg));
        PO_View.checkElementBy(driver, "text", PO_NavView.getP().getString("user.details", lg));
        PO_View.checkElementBy(driver, "text", PO_NavView.getP().getString("user.send", lg));
        PO_View.checkElementBy(driver, "text", PO_NavView.getP().getString("pagination.last", lg));
        PO_View.checkElementByAndClick(driver, "id", "btnPost");
        PO_View.checkElementByAndClick(driver, "id", "btnMisPosts");
        PO_View.checkElementBy(driver, "text", PO_NavView.getP().getString("pagination.last", lg));
        PO_View.checkElementBy(driver, "text", PO_NavView.getP().getString("post.nav", lg));
        PO_View.checkElementBy(driver, "text", PO_NavView.getP().getString("post.list.date", lg));
        PO_View.checkElementBy(driver, "text", PO_NavView.getP().getString("post.list.description", lg));
        PO_View.checkElementByAndClick(driver, "id", "friendshipDropdown");
        PO_View.checkElementByAndClick(driver, "id", "friendshipList");
        PO_View.checkElementBy(driver, "text", PO_NavView.getP().getString("pagination.last", lg));
        PO_View.checkElementBy(driver, "text", PO_NavView.getP().getString("friend.startDate", lg));
        PO_View.checkElementBy(driver, "text", PO_NavView.getP().getString("friend.email", lg));
        PO_View.checkElementBy(driver, "text", PO_NavView.getP().getString("friend.lastPublication", lg));
        PO_LoginView.logout(driver);
        //PO_View.checkElementBy(driver, "text", PO_NavView.getP().getString("login.logout.message", lg));

    }

    @Test
    @Order(32)
    void PR32() {
        checkLenguage(PO_View.getP().getSPANISH());
        checkLenguage(PO_View.getP().getENGLISH());
        checkLenguage(PO_View.getP().getSPANISH());

    }
    @Test
    @Order(33)
    void PR33() {
        checkLenguage(PO_View.getP().getENGLISH());
        checkLenguage(PO_View.getP().getFRENCH());
        checkLenguage(PO_View.getP().getENGLISH());

    }

    @Test
    @Order(34)
    void PR34() {
        driver.navigate().to(URL + "/user/list");
        List<WebElement> elements = PO_View.checkElementBy(driver, "text"
                , PO_View.getP().getString("login.message", PO_Properties.getSPANISH()));
        Assertions.assertTrue(elements.size()>0);

    }
    @Test
    @Order(35)
    void PR35() {
        driver.navigate().to(URL + "/friendship/request/list");
        List<WebElement> elements = PO_View.checkElementBy(driver, "text"
                , PO_View.getP().getString("login.message", PO_Properties.getSPANISH()));
        Assertions.assertTrue(elements.size()>0);

    }
    @Test
    @Order(36)
    void PR36() {
        PO_LoginView.login(driver, "user01@email.com", "Us3r@1-PASSW","Usuarios");
        driver.navigate().to(URL + "/admin/list");
        List<WebElement> elements = PO_View.checkElementBy(driver, "text"
                , "Forbidden");
        Assertions.assertTrue(elements.size()>0);

    }
    @Test
    @Order(37)
    void PR37() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        //Rellenamos el formulario.
        PO_SignUpView.fillForm(driver, "Pepe", "Pérez", "pp@gmail.com", "aA?123456789", "aA?123456789");
        PO_LoginView.logout(driver);
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        //Rellenamos el formulario.
        PO_SignUpView.fillForm(driver, "Pepa", "Pérez", "ppa@gmail.com", "aA?123456789", "aA?123456789");
        PO_LoginView.logout(driver);
        PO_LoginView.login(driver, "admin@email.com", "@@D0r"
                , PO_View.getP().getString("login.error.message", PO_Properties.getSPANISH()));
        PO_LoginView.login(driver, "admin@email.com", "@Dm1n1str@"
                , PO_View.getP().getString("login.error.message", PO_Properties.getSPANISH()));

        PO_LoginView.login(driver, "admin@email.com", "@Dm1n1str@D0r", "Usuarios");
        PO_LoginView.logout(driver);
        PO_LoginView.login(driver, "admin@email.com", "@Dm1n1str@D0r", "Usuarios");
        driver.navigate().to(URL + "/admin/log");
        List<WebElement> elements = PO_AdminUserList.selectXtypeOfLog(driver
                ,"/html/body/div/div/table/tbody","LOGIN_EX");
        Assertions.assertTrue(elements.size()>=2);
        elements = PO_AdminUserList.selectXtypeOfLog(driver
                ,"/html/body/div/div/table/tbody","ALTA");
        Assertions.assertTrue(elements.size()>=2);

        elements = PO_AdminUserList.selectXtypeOfLog(driver
                ,"/html/body/div/div/table/tbody","LOGIN_ERR");
        Assertions.assertTrue(elements.size()>=2);
        elements = PO_AdminUserList.selectXtypeOfLog(driver
                ,"/html/body/div/div/table/tbody","PET");
        Assertions.assertTrue(elements.size()>=2);
        elements = PO_AdminUserList.selectXtypeOfLog(driver
                ,"/html/body/div/div/table/tbody","LOGOUT");
        Assertions.assertTrue(elements.size()>=2);

    }
    @Test
    @Order(38)
    void PR38() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        //Rellenamos el formulario.
        PO_SignUpView.fillForm(driver, "Pepe", "Pérez", "pp@gmail.com", "aA?123456789", "aA?123456789");
        PO_LoginView.logout(driver);
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        //Rellenamos el formulario.
        PO_SignUpView.fillForm(driver, "Pepa", "Pérez", "ppa@gmail.com", "aA?123456789", "aA?123456789");
        PO_LoginView.logout(driver);
        PO_LoginView.login(driver, "admin@email.com", "@@D0r"
                , PO_View.getP().getString("login.error.message", PO_Properties.getSPANISH()));
        PO_LoginView.login(driver, "admin@email.com", "@Dm1n1str@"
                , PO_View.getP().getString("login.error.message", PO_Properties.getSPANISH()));

        PO_LoginView.login(driver, "admin@email.com", "@Dm1n1str@D0r", "Usuarios");
        PO_LoginView.logout(driver);
        PO_LoginView.login(driver, "admin@email.com", "@Dm1n1str@D0r", "Usuarios");
        driver.navigate().to(URL + "/admin/log");
//        List<WebElement> elements = PO_View.checkElementBy(driver, "text"
//                , "LOGIN_EX");
        // /html/body/div/div/table
        List<WebElement> elements = PO_AdminUserList.selectXtypeOfLog(driver
                ,"/html/body/div/div/table/tbody","LOGIN_EX");
        Assertions.assertTrue(elements.size()>=2);
        elements = PO_AdminUserList.selectXtypeOfLog(driver
                ,"/html/body/div/div/table/tbody","ALTA");
        Assertions.assertTrue(elements.size()>=2);

        elements = PO_AdminUserList.selectXtypeOfLog(driver
                ,"/html/body/div/div/table/tbody","LOGIN_ERR");
        Assertions.assertTrue(elements.size()>=2);
        elements = PO_AdminUserList.selectXtypeOfLog(driver
                ,"/html/body/div/div/table/tbody","PET");
        Assertions.assertTrue(elements.size()>=2);
        elements = PO_AdminUserList.selectXtypeOfLog(driver
                ,"/html/body/div/div/table/tbody","LOGOUT");
        Assertions.assertTrue(elements.size()>=2);
        PO_AdminUserList.filter(driver,"LOGIN_EX","//*[@id=\"logType\"]");

//        By boton = By.id("deleteButton");
//        driver.findElement(boton).click();
        PO_View.checkElementByAndClick(driver,"id","deleteButton");

        PO_AdminUserList.filter(driver,"ALL","//*[@id=\"logType\"]");
        elements = PO_AdminUserList.selectXtypeOfLog(driver
                ,"/html/body/div/div/table/tbody","LOGIN_EX");
        Assertions.assertTrue(elements.isEmpty());

    }


}
