package org.psc.workerws.rules;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.scripting.groovy.GroovyScriptFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class RuleEvaluator {

    @Value("classpath:rule1.groovy")
    private Resource rule1Resource;

    @Value("classpath:rule2.groovy")
    private Resource rule2Resource;

    public boolean evaluate() throws IOException {
        var scriptWriter = new StringWriter();
        IOUtils.copy(rule1Resource.getInputStream(), scriptWriter, StandardCharsets.UTF_8);
        var scriptContent = scriptWriter.toString();

//        ScriptSource scriptSource = new StaticScriptSource(scriptContent);
//
//        GroovyScriptFactory scriptFactory = new GroovyScriptFactory("classpath:rule1.groovy");

        var binding = new Binding();
        binding.setVariable("param1", "hello");
        binding.setVariable("printParam", "Hi!");
        var shell = new GroovyShell(binding);
        var script = shell.parse(scriptContent);
        Boolean result = (Boolean) script.run();
        log.info(result.toString());
        return result;
    }

    public boolean evaluate2() throws IOException {
        var scriptWriter = new StringWriter();
        IOUtils.copy(rule2Resource.getInputStream(), scriptWriter, StandardCharsets.UTF_8);
        var scriptContent = scriptWriter.toString();

//        ScriptSource scriptSource = new StaticScriptSource(scriptContent);
//
//        GroovyScriptFactory scriptFactory = new GroovyScriptFactory("classpath:rule1.groovy");

        var binding = new Binding();
        binding.setVariable("param1", "hello");
        var shell = new GroovyShell(binding);
        var script = shell.parse(scriptContent);
        String result = (String) script.invokeMethod("getMeToo", new String[]{"hello", "bye"});
        log.info(result);
        return result.equals("got: hello and bye");
    }

    public boolean evaluateSpring() throws IOException {

        GroovyScriptFactory scriptFactory = new GroovyScriptFactory("classpath");

        return false;
    }
}
