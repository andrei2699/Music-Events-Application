package services.implementation;

public class FileSystemManagerTest {
//
//    private FileSystemManager fileSystemManager;
//    private IUserService userService;
//
//    @Before
//    public void setUp() {
//        userService = new UserServiceImpl(new UserRepository());
//    }
//
//    @After
//    public void tearDown() {
//        fileSystemManager = null;
//    }
//
//    @Test
//    public void createJSONFiles() {
//        try {
//            fileSystemManager.createJSONFiles();
//
//            Assert.assertTrue("Folder data exists", Files.exists(FileSystemManager.getPathToFile("data")));
//
//            Assert.assertTrue("Users JSON file exists", Files.exists(FileSystemManager.getUsersFilePath()));
//            Assert.assertTrue("Bars JSON file exists", Files.exists(FileSystemManager.getBarsFilePath()));
//            Assert.assertTrue("Artists JSON file exists", Files.exists(FileSystemManager.getArtistsFilePath()));
//            Assert.assertTrue("Events JSON file exists", Files.exists(FileSystemManager.getEventsFilePath()));
//            Assert.assertTrue("Reservations JSON file exists", Files.exists(FileSystemManager.getReservationsFilePath()));
//
//        } catch (IOException e) {
//            Assert.fail("Cannot create folder structure");
//        }
//    }
//
//    @Test
//    public void test_readUsers() {
//        try {
//            UserRepository repository = Mockito.mock(UserRepository.class);
//            when(repository.getAll()).thenReturn(new ArrayList<>());
//            when(repository.readById(2)).thenReturn(new UserModel());
//
//            IUserService us = new UserServiceImpl(repository);
//
//            us.updateUser();
//
//            fileSystemManager.createJSONFiles();
//
//            Assert.assertTrue("Folder data exists", Files.exists(FileSystemManager.getPathToFile("data")));
//
//            Assert.assertTrue("Users JSON file exists", Files.exists(FileSystemManager.getUsersFilePath()));
//            Assert.assertTrue("Bars JSON file exists", Files.exists(FileSystemManager.getBarsFilePath()));
//            Assert.assertTrue("Artists JSON file exists", Files.exists(FileSystemManager.getArtistsFilePath()));
//            Assert.assertTrue("Events JSON file exists", Files.exists(FileSystemManager.getEventsFilePath()));
//            Assert.assertTrue("Reservations JSON file exists", Files.exists(FileSystemManager.getReservationsFilePath()));
//
//        } catch (IOException e) {
//            Assert.fail("Cannot create folder structure");
//        }
//    }

}