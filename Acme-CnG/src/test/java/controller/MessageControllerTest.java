package controller;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import controllers.MessageController;
import domain.Message;
import services.ActorService;
import services.MessageService;
import utilities.AbstractTest;
 
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/junit.xml"
	})
@WebAppConfiguration
public class MessageControllerTest extends AbstractTest {
 
    private MockMvc mockMvc;
    
    @Mock
    private MessageService 	messageService;
    
    @Mock
    private ActorService	actorService; 
 
    //Add WebApplicationContext field here
 
    @Before
    @Override
    public void setUp() throws Exception {
    	MockitoAnnotations.initMocks(this);
        Mockito.reset(messageService);
        mockMvc = MockMvcBuilders.standaloneSetup(new MessageController(messageService, actorService)).build();
    }
 
    @Test
    public void showMessageView() throws Exception {
    	authenticate("admin");
    	
    	int messageId;
    	Message message;
    	
    	messageId = 62;
    	message = new Message();
    	message.setId(messageId);
    	
    	Mockito.when(messageService.findOne(messageId)).thenReturn(message);
    	
    	mockMvc.perform(MockMvcRequestBuilders.get("/message/showMessage?messageId="+messageId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("message/showMessage"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("message/showMessage"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("mes"))
                .andExpect(MockMvcResultMatchers.model().attribute("mes", Matchers.hasProperty("id", Matchers.is(messageId))));
 
    	unauthenticate();
    }
    
}
