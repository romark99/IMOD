package com.brest.bstu.po1.imod.dao;

import com.brest.bstu.po1.imod.model.Greeting;

public interface GreetingDao {

    Greeting addGreeting(Greeting greeting);

    Greeting[] getAllGreetings();
}
