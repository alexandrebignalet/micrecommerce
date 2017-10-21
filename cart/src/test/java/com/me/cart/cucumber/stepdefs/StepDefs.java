package com.me.cart.cucumber.stepdefs;

import com.me.cart.CartApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = CartApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
