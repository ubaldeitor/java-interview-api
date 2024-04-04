package com.talentreef.interviewquestions.takehome.services;

import com.talentreef.interviewquestions.takehome.models.Widget;
import com.talentreef.interviewquestions.takehome.respositories.WidgetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class WidgetService {

  private final WidgetRepository widgetRepository;

  @Autowired
  private WidgetService(WidgetRepository widgetRepository) {
    Assert.notNull(widgetRepository, "widgetRepository must not be null");
    this.widgetRepository = widgetRepository;
  }

  public List<Widget> getAllWidgets() {
    return widgetRepository.findAll();
  }

  public Widget getWidgetById(String id){
    Optional<Widget> widget =  widgetRepository.findById(id);
      return widget.orElse(null);
  }

  public Widget addWidget(Widget widget){
    return widgetRepository.save(widget);
  }

  public Widget updateWidget(String id, Widget widget){
    Optional<Widget> widgetToUpdate = widgetRepository.findById(id);
    if (widgetToUpdate.isPresent()) {
      Widget _widget = widgetToUpdate.get();
      _widget.setDescription(widget.getDescription());
      _widget.setPrice(widget.getPrice());
      return widgetRepository.save(_widget);
    } else {
      return null;
    }
  }

  public List<Widget> deleteWidget(String id){
    return widgetRepository.deleteById(id);
  }
}
