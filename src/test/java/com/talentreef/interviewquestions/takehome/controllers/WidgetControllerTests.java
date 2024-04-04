package com.talentreef.interviewquestions.takehome.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talentreef.interviewquestions.takehome.models.Widget;
import com.talentreef.interviewquestions.takehome.services.WidgetService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WidgetControllerTests {

  final private ObjectMapper objectMapper = new ObjectMapper();

  private MockMvc mockMvc;

  @Mock
  private WidgetService widgetService;

  @InjectMocks
  private WidgetController widgetController;

  @Before
  public void init() {
    mockMvc = MockMvcBuilders.standaloneSetup(widgetController).build();
  }

  @Test
  public void when_getAllWidgets_expect_allWidgets() throws Exception {
    Widget widget = Widget.builder().name("Widget von Hammersmark").build();
    List<Widget> allWidgets = List.of(widget);
    when(widgetService.getAllWidgets()).thenReturn(allWidgets);

    MvcResult result = mockMvc.perform(get("/v1/widgets"))
               .andExpect(status().isOk())
               .andDo(print())
               .andReturn();

    List<Widget> parsedResult = objectMapper.readValue(result.getResponse().getContentAsString(),
        new TypeReference<List<Widget>>(){});
    assertThat(parsedResult).isEqualTo(allWidgets);
  }

  @Test
  public void when_getWidgetById_expect_one_widget() throws Exception {
    Widget widget = Widget.builder().name("Widget von Hammersmark").build();
    when(widgetService.getWidgetById("Widget von Hammersmark")).thenReturn(widget);
    MvcResult result = mockMvc.perform(get("/v1/widgets/Widget von Hammersmark"))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();

    Widget parsedResult = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Widget>() {
    });

    assertThat(parsedResult).isEqualTo(widget);
  }

  @Test
  public void when_addWidget_expect_one_widget() throws Exception {
    Widget widget = Widget.builder().name("Widget von Hammersmark").build();
    when(widgetService.addWidget(widget)).thenReturn(widget);

    MvcResult result = mockMvc.perform(post("/v1/widgets")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(asJsonString(widget))
                    .characterEncoding("utf-8"))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();

    Widget parsedResult = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Widget>() {
    });
    assertThat(parsedResult).isEqualTo(widget);
  }

  @Test
  public void when_updateWidget_expect_one_widget() throws Exception {
    Widget widget = Widget.builder().name("Widget von Hammersmark").description("Amazing widget").price(1876.23).build();
    when(widgetService.updateWidget("Widget von Hammersmark", widget)).thenReturn(widget);

    MvcResult result = mockMvc.perform(put("/v1/widgets/Widget von Hammersmark")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(asJsonString(widget))
                    .characterEncoding("utf-8"))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();

    Widget parsedResult = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Widget>() {
    });
    assertThat(parsedResult).isEqualTo(widget);
  }

  @Test
  public void when_deleteWidget_expect_empty_list() throws Exception {
    List<Widget> response = new ArrayList<>();
    when(widgetService.deleteWidget("Widget von Hammersmark")).thenReturn(response);

    MvcResult result = mockMvc.perform(delete("/v1/widgets/Widget von Hammersmark")
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();

    List<Widget> parsedResult = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Widget>>() {
    });
    assertThat(parsedResult).isEqualTo(response);
  }

  public static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
