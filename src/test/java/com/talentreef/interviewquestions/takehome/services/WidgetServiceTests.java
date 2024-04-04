package com.talentreef.interviewquestions.takehome.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.talentreef.interviewquestions.takehome.models.Widget;
import com.talentreef.interviewquestions.takehome.respositories.WidgetRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
public class WidgetServiceTests {

  @Mock
  private WidgetRepository widgetRepository;

  @InjectMocks
  private WidgetService widgetService;

  @Test
  public void when_getAllWidgets_expect_findAllResult() throws Exception {
    Widget widget = Widget.builder().name("Widgette Nielson").build();
    List<Widget> response = List.of(widget);
    when(widgetRepository.findAll()).thenReturn(response);

    List<Widget> result = widgetService.getAllWidgets();

    assertThat(result).isEqualTo(response);
  }

  @Test
  public void when_getWidgetByName_expect_one_widget() throws Exception {
    Widget response = Widget.builder().name("Widgette Nielson").build();
    when(widgetRepository.findById("Widgette Nielson")).thenReturn(Optional.ofNullable(response));

    Widget result = widgetService.getWidgetById("Widgette Nielson");
    assertThat(result).isEqualTo(response);
  }

  @Test
  public void when_addWidget_expect_getAllWidgets_not_null() throws Exception {
    Widget widget = Widget.builder().name("Widgette Nielson").build();
    when(widgetRepository.save(widget)).thenReturn(widget);

    Widget result = widgetService.addWidget(widget);

    assertThat(result).isEqualTo(widget);
  }

  @Test
  public void when_updateWidget_expect_old_not_equals_to_new() throws Exception {
    Widget widget = Widget.builder().name("Widgette Nielson").description("Amazing widget").price(2999.99).build();
    Widget widgetUpdated = Widget.builder().name("Widgette Nielson").description("Amazing widgette").price(2599.99).build();
    when(widgetRepository.findById("Widgette Nielson")).thenReturn(Optional.of(widget));
    when(widgetRepository.save(widgetUpdated)).thenReturn(widgetUpdated);


    Widget result = widgetService.updateWidget("Widgette Nielson", widgetUpdated);

    assertThat(result).isEqualTo(widgetUpdated);
  }

  @Test
  public void when_deleteWidget_expect_findAll_empty() {
    List<Widget> table = new ArrayList<>();
    when(widgetRepository.deleteById("Widgette Nielson")).thenReturn(table);

    List<Widget> result = widgetService.deleteWidget("Widgette Nielson");

    assertThat(result).isEqualTo(table);
  }
}
