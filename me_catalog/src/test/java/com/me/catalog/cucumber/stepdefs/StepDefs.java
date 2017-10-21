package com.me.catalog.cucumber.stepdefs;

import com.me.catalog.CatalogApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = CatalogApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
