package com.uniovi.sdi2324entrega1ext205;

import com.uniovi.sdi2324entrega122.entities.User;
import com.uniovi.sdi2324entrega122.pageobjects.*;
import com.uniovi.sdi2324entrega122.repositories.UsersRepository;
import com.uniovi.sdi2324entrega122.services.RolesService;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ApplicationTests {

//    @Value("${spring.data.web.pageable.size-parameter}")
//    int pageSize;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private RolesService rolesService;
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
    public void setUp(){
        driver.navigate().to(URL);
    }
    @AfterEach
    public void tearDown(){
        driver.manage().deleteAllCookies();
    }
    @BeforeAll
    static public void begin() {}
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
    void PR1() {
        //Vamos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        //Rellenamos el formulario.
        PO_SignUpView.fillForm(driver, "Pepe", "Pérez", "pp@gmail.com", "aA?123456789", "aA?123456789");
        //Comprobamos que entramos en la sección privada y nos nuestra el texto a buscar
        String checkText = "bienvenido";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    @Test
    @Order(2)
    void PR2() throws InterruptedException {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        PO_SignUpView.fillForm(driver, "", "", "", "77777", "77777");

        Thread.sleep(2000);
        List<WebElement> name = PO_SignUpView.checkElementByKey(driver, "Error.empty.name",
                PO_Properties.getSPANISH() );
        List<WebElement> lastname = PO_SignUpView.checkElementByKey(driver, "Error.empty.lastName",
                PO_Properties.getSPANISH() );
        List<WebElement> password = PO_SignUpView.checkElementByKey(driver, "Error.signup.password.robust",
                PO_Properties.getSPANISH() );
        List<WebElement> email = PO_SignUpView.checkElementByKey(driver, "Error.empty.email",
                PO_Properties.getSPANISH() );

        //Comprobar error nombre vacío
        String checkText = PO_HomeView.getP().getString("Error.empty.name", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText , name.get(0).getText());
        //Comprobar error apellido vacío

        checkText = PO_HomeView.getP().getString("Error.empty.lastName", PO_Properties.getSPANISH());

        Assertions.assertEquals(checkText , lastname.get(0).getText());
        //Comprobar error email vacío

        checkText = PO_HomeView.getP().getString("Error.empty.email", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText , email.get(0).getText().split("\\n")[0]);
        //Comprobar error password vacío

        checkText = PO_HomeView.getP().getString("Error.signup.password.robust", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText , password.get(0).getText().split("\\n")[0]);
    }

    @Test
    @Order(3)
    void PR03() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");

        PO_SignUpView.fillForm(driver, "Pepe", "Pérez", "pp@gmail.com", "aA?123456789", "aA?123456788");
        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.passwordConfirm.coincidence",
                PO_Properties.getSPANISH() );
        String checkText = PO_HomeView.getP().getString("Error.signup.passwordConfirm.coincidence", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText , result.get(0).getText());
    }

    @Test
    @Order(4)
    void PR04() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");

        PO_SignUpView.fillForm(driver, "Pepe", "Pérez", "test0@email.com", "aA?123456789", "aA?123456789");
        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.email.already.registered",
                PO_Properties.getSPANISH() );
        String checkText = PO_HomeView.getP().getString("Error.email.already.registered", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText , result.get(0).getText());
    }

    @Test
    @Order(5)
    public void PR05() {
        PO_LoginView.login(driver, "admin@email.com", "@Dm1n1str@D0r","bienvenido");
    }

    @Test
    @Order(6)
    public void PR06() {
        PO_LoginView.login(driver, "test1@email.com", "123456","bienvenido");
    }

    @Test
    @Order(7)
    public void PR07() {
        PO_LoginView.login(driver, "", "",PO_HomeView.getP().getString("login.message", PO_Properties.getSPANISH()));
    }

    @Test
    @Order(8)
    public void PR08() {
        PO_LoginView.login(driver, "test1@email.com", "abcdefg",PO_HomeView.getP().getString("login.error.message", PO_Properties.getSPANISH()));
    }

    @Test
    @Order(9)
    void PR09() {
        PO_LoginView.login(driver, "test1@email.com", "123456","bienvenido");

        PO_NavView.clickOption(driver, "logout", "class", "btn btn-primary");

        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "login.logout.message",
                PO_Properties.getSPANISH() );
        String checkText = PO_HomeView.getP().getString("login.logout.message", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText , result.get(0).getText());
    }


    @Test
    @Order(10)
    void PR010() {
        PO_LoginView.login(driver, "test1@email.com", "123456","bienvenido");
        PO_NavView.clickOption(driver, "logout", "class", "btn btn-primary");
        Assertions.assertThrows(TimeoutException.class, ()-> PO_NavView.clickOption(driver, "logout", "class", "btn btn-primary"));
    }

    @Test
    @Order(11)
    public void PR11(){
        PO_LoginView.login(driver, "admin@email.com", "@Dm1n1str@D0r","bienvenido");
        PO_View.checkElementByAndClick(driver,"id","userDropdown");
        PO_View.checkElementByAndClick(driver,"id","adminUserList");
        //"/html/body/div/div/form/table/tbody/tr[1]"
        //List<WebElement> elements = PO_View.checkElementBy(driver,"free","");
        List<User> userlist = new ArrayList<User>();
        usersRepository.findAll().forEach(userlist::add);
        int size = userlist.size();
//        for (int i=0 ; i<size%pageSize ; i++) {
//
//        }
//        int page =1;
        List<WebElement> elements = PO_View.checkElementBy(driver,"free","/html/body/div/div/form/table/tbody/tr");
        if(PO_PrivateView.pageOptionChecked(driver,"//a[contains(@class, 'page-link')]",1))
            elements.addAll(PO_View.checkElementBy(driver,"free","/html/body/div/div/form/table/tbody/tr"));
        while (PO_PrivateView.pageOptionChecked(driver,"//a[contains(@class, 'page-link')]",3)){
            elements.addAll(PO_View.checkElementBy(driver,"free","/html/body/div/div/form/table/tbody/tr"));
        }
        Assertions.assertEquals(size, elements.size());

    }

    @Test
    @Order(12)
    public void PR12(){
        PO_LoginView.login(driver, "admin@email.com", "@Dm1n1str@D0r","bienvenido");
        PO_View.checkElementByAndClick(driver,"id","userDropdown");
        PO_View.checkElementByAndClick(driver,"id","adminUserList");
        User oldUser = PO_AdminUserList.getUserPos(driver,1,"/html/body/div/div/form/table/tbody");

        PO_AdminUserList.getUserPos(driver,1,"/html/body/div/div/form/table/tbody");
        PO_AdminUserList.clickModUserPos(driver,1,"html/body/div/div/form/table/tbody");
        PO_AdminUserList.modUserForm(driver,"pepe2@email.com","pepe2","pepito"
                ,rolesService.getRoles()[1]);

        PO_View.checkElementByAndClick(driver,"id","userDropdown");
        PO_View.checkElementByAndClick(driver,"id","adminUserList");
        User newUser = PO_AdminUserList.getUserPos(driver,1,"/html/body/div/div/form/table/tbody");
        Assertions.assertEquals(newUser.getEmail(),"pepe2@email.com");
        Assertions.assertEquals(newUser.getName(),"pepe2");
        Assertions.assertEquals(newUser.getLastName(),"pepito");
        Assertions.assertEquals(newUser.getRole(),rolesService.getRoles()[1]);
        PO_PrivateView.logoutUser(driver,PO_Properties.getSPANISH());
        PO_LoginView.login(driver, "pepe2@email.com", "123456","bienvenido");
        PO_View.checkElementByAndClick(driver,"id","userDropdown");
        PO_View.checkElementByAndClick(driver,"id","adminUserList");
        List<WebElement> e = PO_View.checkElementBy(driver,"text","borrar");
        Assertions.assertTrue(e.size()>0);


    }

    @Test
    @Order(13)
    public void PR13(){
        PO_LoginView.login(driver, "admin@email.com", "@Dm1n1str@D0r","bienvenido");
        PO_View.checkElementByAndClick(driver,"id","userDropdown");
        PO_View.checkElementByAndClick(driver,"id","adminUserList");
        User oldUser = PO_AdminUserList.getUserPos(driver,1,"/html/body/div/div/form/table/tbody");

        PO_AdminUserList.getUserPos(driver,1,"/html/body/div/div/form/table/tbody");
        PO_AdminUserList.clickModUserPos(driver,1,"html/body/div/div/form/table/tbody");
        PO_AdminUserList.modUserForm(driver,"test2@email.com"," "," "
                ,rolesService.getRoles()[1]);
        List<String> errorMsgList = new ArrayList<>();
        errorMsgList.add(PO_HomeView.getP().getString("Error.email.already.registered", PO_Properties.getSPANISH()));
        errorMsgList.add(PO_HomeView.getP().getString("Error.name.whitespaces", PO_Properties.getSPANISH()));
        errorMsgList.add(PO_HomeView.getP().getString("Error.lastName.whitespaces", PO_Properties.getSPANISH()));
        Assertions.assertTrue(errorMsgList.size()==3 && errorMsgList.stream().noneMatch(s->s.trim().isEmpty()));


        PO_View.checkElementByAndClick(driver,"id","userDropdown");
        PO_View.checkElementByAndClick(driver,"id","adminUserList");
        User newUser = PO_AdminUserList.getUserPos(driver,1,"/html/body/div/div/form/table/tbody");
        Assertions.assertEquals(newUser.getEmail(),oldUser.getEmail());
        Assertions.assertEquals(newUser.getName(),oldUser.getName());
        Assertions.assertEquals(newUser.getLastName(),oldUser.getLastName());
        Assertions.assertEquals(newUser.getRole(),rolesService.getRoles()[0]);


    }
    @Test
    @Order(14)
    public void PR14(){
        PO_LoginView.login(driver, "admin@email.com", "@Dm1n1str@D0r","bienvenido");
        PO_View.checkElementByAndClick(driver,"id","userDropdown");
        PO_View.checkElementByAndClick(driver,"id","adminUserList");
        User oldUser = PO_AdminUserList.getUserPos(driver,1,"/html/body/div/div/form/table/tbody");
        PO_AdminUserList.deleteUserListPos(driver, Arrays.asList(1),"/html/body/div/div/form/table/tbody");
        PO_View.checkElementByAndClick(driver,"id","userDropdown");
        PO_View.checkElementByAndClick(driver,"id","adminUserList");
        User otherUser = PO_AdminUserList.getUserPos(driver,1,"/html/body/div/div/form/table/tbody");
        Assertions.assertNotEquals(otherUser.getEmail(),oldUser.getEmail());
        Assertions.assertNotEquals(otherUser.getName(),oldUser.getName());
        Assertions.assertNotEquals(otherUser.getLastName(),oldUser.getLastName());


    }
    @Test
    @Order(15)
    public void PR15(){
        PO_LoginView.login(driver, "admin@email.com", "@Dm1n1str@D0r","bienvenido");
        PO_View.checkElementByAndClick(driver,"id","userDropdown");
        PO_View.checkElementByAndClick(driver,"id","adminUserList");
        PO_PrivateView.pageOptionChecked(driver,"//a[contains(@class, 'page-link')]",3);
        List<WebElement> elements = PO_View.checkElementBy(driver,"free"
                ,"/html/body/div/div/form/table/tbody/tr");
        int last = elements.size()-1;
        User oldUser = PO_AdminUserList.getUserPos(driver,last,"/html/body/div/div/form/table/tbody");
        PO_AdminUserList.deleteUserListPos(driver, Arrays.asList(last),"/html/body/div/div/form/table/tbody");
        PO_View.checkElementByAndClick(driver,"id","userDropdown");
        PO_View.checkElementByAndClick(driver,"id","adminUserList");

        PO_PrivateView.pageOptionChecked(driver,"//a[contains(@class, 'page-link')]",3);
        elements = PO_View.checkElementBy(driver,"free"
                ,"/html/body/div/div/form/table/tbody/tr");
        last = elements.size()-1;
        User otherUser = PO_AdminUserList.getUserPos(driver,1,"/html/body/div/div/form/table/tbody");
        Assertions.assertNotEquals(otherUser.getEmail(),oldUser.getEmail());
        Assertions.assertNotEquals(otherUser.getName(),oldUser.getName());
        Assertions.assertNotEquals(otherUser.getLastName(),oldUser.getLastName());


    }

    @Test
    @Order(16)
    public void PR16(){
        PO_LoginView.login(driver, "admin@email.com", "@Dm1n1str@D0r","bienvenido");
        PO_View.checkElementByAndClick(driver,"id","userDropdown");
        PO_View.checkElementByAndClick(driver,"id","adminUserList");
        Integer[] ids = {2,3,4};
        List<User> oldUsers = new ArrayList<>();
        Arrays.stream(ids).forEach(id->
                oldUsers.add(PO_AdminUserList.getUserPos(driver,id,"/html/body/div/div/form/table/tbody"))
                );
        PO_AdminUserList.deleteUserListPos(driver, Arrays.asList(ids),"/html/body/div/div/form/table/tbody");
        PO_View.checkElementByAndClick(driver,"id","userDropdown");
        PO_View.checkElementByAndClick(driver,"id","adminUserList");

        List<User> otherUsers = new ArrayList<>();
        Arrays.stream(ids).forEach(id->
                otherUsers.add(PO_AdminUserList.getUserPos(driver,id,"/html/body/div/div/form/table/tbody"))
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
    @Order(17)
    void PR17() {
        String email = "test1@email.com";
        PO_LoginView.login(driver, email, "123456","bienvenido");
        PO_NavView.clickOption(driver, "user/list", "class", "btn btn-primary");

        WebElement searchText = driver.findElement(By.name("searchText"));
        searchText.click();
        searchText.clear();

        PO_View.checkElementByAndClick(driver, "text", "Buscar");

        List<WebElement> usersList = SeleniumUtils.waitLoadElementsBy(driver, "free", "//tbody/tr",
                PO_View.getTimeout());
        Assertions.assertEquals(pageSize, usersList.size());

        Assertions.assertThrows(TimeoutException.class, ()-> PO_View.checkElementBy(driver, "text", email));

        PO_NavView.clickOption(driver, "?page=5", "class", "btn btn-primary");

        Assertions.assertThrows(TimeoutException.class, ()-> PO_View.checkElementBy(driver, "text", "admin@email.com"));
        Assertions.assertThrows(TimeoutException.class, ()-> PO_View.checkElementBy(driver, "text", "admin"));
        Assertions.assertThrows(TimeoutException.class, ()-> PO_View.checkElementBy(driver, "text", "admin2@email.com"));
    }

    @Test
    @Order(18)
    void PR18() {
        String email = "test1@email.com";
        PO_LoginView.login(driver, email, "123456","bienvenido");
        PO_NavView.clickOption(driver, "user/list", "class", "btn btn-primary");

        PO_View.checkElementByAndClick(driver, "text", "Buscar");

        Assertions.assertThrows(TimeoutException.class, ()-> PO_View.checkElementBy(driver, "text", email));

        PO_NavView.clickOption(driver, "?page=5", "class", "btn btn-primary");

        Assertions.assertThrows(TimeoutException.class, ()-> PO_View.checkElementBy(driver, "text", "admin@email.com"));
        Assertions.assertThrows(TimeoutException.class, ()-> PO_View.checkElementBy(driver, "text", "admin"));
        Assertions.assertThrows(TimeoutException.class, ()-> PO_View.checkElementBy(driver, "text", "admin2@email.com"));
    }

    @Test
    @Order(19)
    void PR19() {
        String email = "test1@email.com";
        PO_LoginView.login(driver, email, "123456","bienvenido");
        PO_NavView.clickOption(driver, "user/list", "class", "btn btn-primary");

        WebElement searchText = driver.findElement(By.name("searchText"));
        searchText.click();
        searchText.clear();
        searchText.sendKeys("amarillo");

        PO_View.checkElementByAndClick(driver, "text", "Buscar");
        Assertions.assertThrows(TimeoutException.class, ()->SeleniumUtils.waitLoadElementsBy(driver, "free", "//tbody/tr",
                PO_View.getTimeout()));

        searchText = driver.findElement(By.name("searchText"));
        searchText.click();
        searchText.clear();
        PO_View.checkElementByAndClick(driver, "text", "Buscar");

        PO_View.checkElementByAndClick(driver, "text", "Buscar");
    }

    @Test
    @Order(20)
    void PR20() {
        String email = "test1@email.com";
        PO_LoginView.login(driver, email, "123456","bienvenido");
        PO_NavView.clickOption(driver, "user/list", "class", "btn btn-primary");

        WebElement searchText = driver.findElement(By.name("searchText"));
        searchText.click();
        searchText.clear();
        searchText.sendKeys("test0");

        PO_View.checkElementByAndClick(driver, "text", "Buscar");
        List<WebElement> usersList = SeleniumUtils.waitLoadElementsBy(driver, "free", "//tbody/tr",
                PO_View.getTimeout());
        Assertions.assertEquals(1, usersList.size());

        PO_View.checkElementBy(driver, "text", "test0@email.com");
        PO_View.checkElementBy(driver, "text", "name0");
        PO_View.checkElementBy(driver, "text", "lastname0");

        searchText = driver.findElement(By.name("searchText"));
        searchText.click();
        searchText.clear();
        PO_View.checkElementByAndClick(driver, "text", "Buscar");

    }

    @Test
    @Order(21)
    //Desde el listado de usuarios de la aplicación, enviar una invitación de amistad a un usuario.
    // Comprobar que la solicitud de amistad aparece en el listado de invitaciones
    public void PR21(){
        PO_LoginView.login(driver, "test0@email.com", "123456","bienvenido");
        //Acceder a la pantalla de usuarios
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//*[@id='myNavbar']/ul[1]/li[3]");
        elements.get(0).click();

        //Enviar solicitud de amistad al usuario con correo test1@email.com
        By enlace = By.xpath("//td[contains(text(), 'test1@email.com')]/following-sibling::*[3]");
        driver.findElement(enlace).click();
        //Cerrar sesión
        PO_PrivateView.logoutUser(driver,  PO_Properties.getSPANISH());
        //Iniciar sesión con test1@email.com
        PO_LoginView.fillLoginForm(driver, "test1@email.com", "123456");
        //Acceder a las solicitudes de amistad
        elements = PO_View.checkElementBy(driver, "free", "//*[@id='myNavbar']/ul[1]/li[4]");
        elements.get(0).click();

        elements = PO_View.checkElementBy(driver, "free", "//a[contains(@href, 'friendship/request/list')]");
        elements.get(0).click();
        //Buscar test0@email.com entre las solicitudes
        enlace = By.xpath("//td[contains(text(), 'test0@email.com')]");
        //Comprobar que la solicitud se encuentra en la tabla
        Assertions.assertNotNull(driver.findElement(enlace));
        //Cerrar sesión
        PO_PrivateView.logoutUser(driver,  PO_Properties.getSPANISH());
    }

    @Test
    @Order(22)
    //Desde el listado de usuarios de la aplicación, enviar una invitación de amistad a un usuario al que ya le
    // habíamos enviado la invitación previamente. No debería dejarnos enviar la invitación.
    // Se podría ocultar el botón de enviar invitación o notificar que ya había sido enviada previamente.
    public void PR22(){
        PO_LoginView.login(driver, "test0@email.com", "123456","bienvenido");
        //Acceder a la pantalla de usuarios
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//*[@id='myNavbar']/ul[1]/li[3]");
        elements.get(0).click();

        //Enviar solicitud de amistad al usuario con correo test1@email.com
        By enlace = By.xpath("//td[contains(text(), 'test1@email.com')]/following-sibling::*[3]");
        driver.findElement(enlace).click();
        String checkText = "Solicitud pendiente";
        //En enlace ya no está disponible, hay texto indicando que hay una solicitud pendiente
        Assertions.assertEquals( checkText,driver.findElement(enlace).getText());
        //Cerrar sesión
        PO_PrivateView.logoutUser(driver,  PO_Properties.getSPANISH());

    }
    @Test
    @Order(23)
    //Mostrar el listado de invitaciones de amistad recibidas.
    // Comprobar con un listado que contenga varias invitaciones recibidas.
    public void PR23(){

        //Inicio sesión como juan que tiene 6 peticiones pendientes
        PO_LoginView.login(driver, "juan@gmail.com", "123456","bienvenido");
        //Compruebo que solo se muestran tantas como indica la pagicanión y que son correctas
        String pending1 = "'jorge@gmail.com'";
        String pending2 = "'pepe@gmail.com'";
        String pending3 = "'paca@gmail.com'";
        String pending4 = "'lara@gmail.com'";
        String pending5 = "'maria@gmail.com'";
        //Acceder a las solicitudes pendientes de amistad
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//*[@id='myNavbar']/ul[1]/li[4]");
        elements.get(0).click();
        elements = PO_View.checkElementBy(driver, "free", "//a[contains(@href, 'friendship/request/list')]");
        elements.get(0).click();
        //Compruebo cada solicitud
        By enlace = By.xpath("//td[contains(text(), "+pending1+")]");
        Assertions.assertNotNull(driver.findElement(enlace));
        //Compruebo cada solicitud
        enlace = By.xpath("//td[contains(text(), "+pending2+")]");
        Assertions.assertNotNull(driver.findElement(enlace));
        //Compruebo cada solicitud
        enlace = By.xpath("//td[contains(text(), "+pending3+")]");
        Assertions.assertNotNull(driver.findElement(enlace));
        //Compruebo cada solicitud
        enlace = By.xpath("//td[contains(text(), "+pending4+")]");
        Assertions.assertNotNull(driver.findElement(enlace));
        //Compruebo cada solicitud
        enlace = By.xpath("//td[contains(text(), "+pending5+")]");
        Assertions.assertNotNull(driver.findElement(enlace));
        //Compruebo que el número se corresponde a la paginación
        List<WebElement> requestsList = SeleniumUtils.waitLoadElementsBy(driver, "free", "//tbody/tr",
                PO_View.getTimeout());
        Assertions.assertEquals(pageSize, requestsList.size());
        PO_PrivateView.logoutUser(driver,  PO_Properties.getSPANISH());
    }

    @Test
    @Order(24)
    //Sobre el listado de invitaciones recibidas. Hacer clic en el botón/enlace de una de ellas y comprobar que
        // dicha solicitud desaparece del listado de invitaciones
    void PR24(){
        //Inicio sesión como juan que tiene 6 peticiones pendientes
        PO_LoginView.login(driver, "juan@gmail.com", "123456","bienvenido");
        //Acceder a las solicitudes pendientes de amistad
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//*[@id='myNavbar']/ul[1]/li[4]");
        elements.get(0).click();
        elements = PO_View.checkElementBy(driver, "free", "//a[contains(@href, 'friendship/request/list')]");
        elements.get(0).click();
        //Aceptar la solicitud de amistad de jorge@gmail.com
        By enlace = By.xpath("//td[contains(text(), 'jorge@gmail.com')]/following-sibling::*[2]");
        driver.findElement(enlace).click();
        //Intentar encontrar la misma solicitud pero no está
        NoSuchElementException exception = Assertions.assertThrows(NoSuchElementException.class, () ->{ driver.findElement(enlace);});
        PO_PrivateView.logoutUser(driver,  PO_Properties.getSPANISH());

    }

    @Test
    @Order(25)
    //Mostrar el listado de amigos de un usuario. Comprobar que el listado contiene los amigos que deben ser.
    void PR25(){
        //Inicio sesión como juan que tiene 1 amigo
        PO_LoginView.login(driver, "juan@gmail.com", "123456","bienvenido");
        //Acceder al listado de amigos
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//*[@id='myNavbar']/ul[1]/li[4]");
        elements.get(0).click();
        elements = PO_View.checkElementBy(driver, "free", "//a[contains(@href, 'friendship/list')]");
        elements.get(0).click();
        //Comprobar que aparece el usuario que es amigo de juan
        elements = PO_View.checkElementBy(driver, "text", "ana@gmail.com");
        Assertions.assertEquals("ana@gmail.com", elements.get(0).getText());
        PO_PrivateView.logoutUser(driver,  PO_Properties.getSPANISH());
    }

    @Test
    @Order(26)
    //Mostrar el listado de amigos de un usuario. Comprobar que se incluye la información relacionada con la última
        // publicación de cada usuario y la fecha de inicio de amistad.
    void PR26(){
        //Inicio sesión como juan que tiene una petición pendiente de jorge
        PO_LoginView.login(driver, "juan@gmail.com", "123456","bienvenido");
        //Acceder a las solicitudes pendientes de amistad
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//*[@id='myNavbar']/ul[1]/li[4]");
        elements.get(0).click();
        elements = PO_View.checkElementBy(driver, "free", "//a[contains(@href, 'friendship/request/list')]");
        elements.get(0).click();

        //Aceptar la solicitud de amistad de jorge@gmail.com
        By enlace = By.xpath("//td[contains(text(), 'jorge@gmail.com')]/following-sibling::*[2]");
        driver.findElement(enlace).click();
        //Cerramos sesión
        PO_PrivateView.logoutUser(driver,  PO_Properties.getSPANISH());

        //Iniciamos sesión con jorge y creamos dos publicaciones
        PO_LoginView.login(driver, "jorge@gmail.com", "123456", "bienvenido");

        // Añadimos una publicación
        elements = PO_View.checkElementBy(driver, "free", "//*[@id='myNavbar']/ul[1]/li[2]");
        elements.get(0).click();
        elements = PO_View.checkElementBy(driver, "free", "//a[contains(@href, 'post/add')]");
        elements.get(0).click();

        // Rellenamos el formulario para añadir la publicación
        String checkDesc = "Ejemplo description";
        String checkTitle = "Ejemplo title";
        PO_PostsView.fillFormAddPost(driver, checkTitle, checkDesc);

        // Añadimos otra publicación
        elements = PO_View.checkElementBy(driver, "free", "//*[@id='myNavbar']/ul[1]/li[2]");
        elements.get(0).click();
        elements = PO_View.checkElementBy(driver, "free", "//a[contains(@href, 'post/add')]");
        elements.get(0).click();

        // Rellenamos el formulario para añadir la publicación
        checkDesc = "Ejemplo description 2";
        checkTitle = "Ejemplo title 2";
        PO_PostsView.fillFormAddPost(driver, checkTitle, checkDesc);

        //Cerramos sesión con jorge
        PO_PrivateView.logoutUser(driver,  PO_Properties.getSPANISH());

        //Volvemos a iniciar sesión con juan y accedemos al listado de amigos
        PO_LoginView.login(driver, "juan@gmail.com", "123456","bienvenido");
        elements = PO_View.checkElementBy(driver, "free", "//*[@id='myNavbar']/ul[1]/li[4]");
        elements.get(0).click();
        elements = PO_View.checkElementBy(driver, "free", "//a[contains(@href, 'friendship/list')]");
        elements.get(0).click();

        //Comprobamos que ana no tiene publicaciones
        WebElement anaCell = driver.findElement(By.xpath("/html/body/div/div/table/tbody/tr[1]/td[5]"));
        Assertions.assertEquals("Este usuario aún no ha realizado ninguna publicación", anaCell.getText());

        //Comprobar que jorge tiene la última publicación que ha subido
        WebElement jorgeCell = driver.findElement(By.xpath("/html/body/div/div/table/tbody/tr[2]/td[5]"));
        Assertions.assertTrue(jorgeCell.getText().contains(checkTitle));



        //Cerramos sesión
        PO_PrivateView.logoutUser(driver,  PO_Properties.getSPANISH());
    }
    @Test
    @Order(27)
    void PR27() {
        PO_LoginView.login(driver, "test0@email.com", "123456", "bienvenido");

        // Pinchamos en la opción de menú de Posts
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//*[@id='myNavbar']/ul[1]/li[2]");
        elements.get(0).click();

        // Pinchamos en añadir publicacion
        elements = PO_View.checkElementBy(driver, "free", "//a[contains(@href, 'post/add')]");
        elements.get(0).click();

        // Rellenamos el formulario para añadir la publicación
        String checkDesc = "Ejemplo description";
        String checkTitle = "Ejemplo title";
        PO_PostsView.fillFormAddPost(driver, checkTitle, checkDesc);

        // Comprobamos que aparezca el nuevo post
        navigateToPage(0, "//a[contains(@class, 'page-link')]");
        elements = PO_View.checkElementBy(driver, "text", checkTitle);
        Assertions.assertEquals(checkTitle, elements.get(0).getText());

        PO_LoginView.logout(driver);
    }
    @Test
    @Order(28)
    void PR28() {
        PO_LoginView.login(driver, "test0@email.com", "123456", "bienvenido");

        // Pinchamos en la opción de menú de Posts
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//*[@id='myNavbar']/ul[1]/li[2]");
        elements.get(0).click();

        // Pinchamos en añadir publicacion
        elements = PO_View.checkElementBy(driver, "free", "//a[contains(@href, 'post/add')]");
        elements.get(0).click();

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

        PO_LoginView.logout(driver);
    }
    @Test
    @Order(29)
    void PR29() {
        PO_LoginView.login(driver, "test0@email.com", "123456", "bienvenido");

        // Pinchamos en la opción de menú de Posts
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//*[@id='myNavbar']/ul[1]/li[2]");
        elements.get(0).click();
        // Pinchamos en añadir publicacion
        elements = PO_View.checkElementBy(driver, "free", "//a[contains(@href, 'post/add')]");
        elements.get(0).click();
        // Rellenamos el formulario para añadir la publicación
        String checkDescA = "Ejemplo description A";
        String checkTitleA = "Ejemplo title A";
        PO_PostsView.fillFormAddPost(driver, checkTitleA, checkDescA);

        // Vamos otra vez a la opción de menú Posts
        elements = PO_View.checkElementBy(driver, "free", "//*[@id='myNavbar']/ul[1]/li[2]");
        elements.get(0).click();
        // Pinchamos en añadir publicacion
        elements = PO_View.checkElementBy(driver, "free", "//a[contains(@href, 'post/add')]");
        elements.get(0).click();
        // Rellenamos el formulario para añadir la publicación
        String checkDescB = "Ejemplo description B";
        String checkTitleB = "Ejemplo title B";
        PO_PostsView.fillFormAddPost(driver, checkTitleB, checkDescB);

        // Comprobamos que aparezcan los nuevos posts
        navigateToPage(0, "//a[contains(@class, 'page-link')]");
        elements = PO_View.checkElementBy(driver, "text", checkTitleA);
        Assertions.assertEquals(checkTitleA, elements.get(0).getText());
        elements = PO_View.checkElementBy(driver, "text", checkTitleB);
        Assertions.assertEquals(checkTitleB, elements.get(0).getText());

        PO_LoginView.logout(driver);
    }
    @Test
    @Order(30)
    void PR30() {
        PO_LoginView.login(driver, "ana@gmail.com", "123456", "bienvenido");

        // Pinchamos en la opción de menú de Posts
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//*[@id='myNavbar']/ul[1]/li[2]");
        elements.get(0).click();

        // Pinchamos en añadir publicacion
        elements = PO_View.checkElementBy(driver, "free", "//a[contains(@href, 'post/add')]");
        elements.get(0).click();

        // Rellenamos el formulario para añadir la publicación
        String checkDesc = "Ejemplo description";
        String checkTitle = "Ejemplo title";
        PO_PostsView.fillFormAddPost(driver, checkTitle, checkDesc);

        // Comprobamos que aparezca el nuevo post
        navigateToPage(0, "//a[contains(@class, 'page-link')]");
        elements = PO_View.checkElementBy(driver, "text", checkTitle);
        Assertions.assertEquals(checkTitle, elements.get(0).getText());

        PO_LoginView.logout(driver);

        // Iniciamos sesión con la cuenta de un amigo
        PO_LoginView.login(driver, "juan@gmail.com", "123456", "bienvenido");

        // Navegar al perfil de Ana y ver si sale su última publicación
        // Vamos al menu de amigos del navbar
        elements = PO_View.checkElementBy(driver, "free", "//*[@id='myNavbar']/ul[1]/li[4]");
        elements.get(0).click();
        // Pinchamos en ver Lista de amigos
        elements = PO_View.checkElementBy(driver, "free", "//a[contains(@href, 'friendship/list')]");
        elements.get(0).click();
        // Pinchamos en Ana
        elements = PO_View.checkElementBy(driver, "free", "//*[@id='friends']/td[6]/div[1]");
        elements.get(0).click();
        // Comprobamos que sale su publicación
        elements = PO_View.checkElementBy(driver, "text", checkTitle);
        Assertions.assertEquals(checkTitle, elements.get(0).getText());

        PO_LoginView.logout(driver);
    }
    @Test
    @Order(31)
    void PR31() {
        PO_LoginView.login(driver, "test0@email.com", "123456", "bienvenido");

        // Intentamos navegar al perfil de Ana (id=24)
        driver.navigate().to(URL + "/user/24");

        // Comprobamos que nos redirige a la pantalla de inicio
        List<WebElement>  elements = PO_View.checkElementBy(driver, "text", "bienvenido");
        Assertions.assertEquals("bienvenido", elements.get(0).getText());

        PO_LoginView.logout(driver);
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
        PO_LoginView.login(driver, "test0@email.com", "123456", "bienvenido");
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

        PO_LoginView.login(driver, "admin@email.com", "@Dm1n1str@D0r", "bienvenido");
        PO_LoginView.logout(driver);
        PO_LoginView.login(driver, "admin@email.com", "@Dm1n1str@D0r", "bienvenido");
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

        PO_LoginView.login(driver, "admin@email.com", "@Dm1n1str@D0r", "bienvenido");
        PO_LoginView.logout(driver);
        PO_LoginView.login(driver, "admin@email.com", "@Dm1n1str@D0r", "bienvenido");
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
