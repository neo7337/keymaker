package com.app.keymaker.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

public class EmailTemplate {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public String templateId;
    public String template;
    public Map<String, String> replacementParams;

    public EmailTemplate(String templateId) {
        this.templateId = templateId;
        try {
            this.template = loadTemplate(templateId);
        } catch (Exception e) {
            this.template = "Empty";
        }
    }

    private String loadTemplate(String templateId) throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        logger.info(templateId);
        File file = new File(classLoader.getResource(templateId).getFile());

        // File file = new File("../../email-html-templates/SendOtp.html");
        logger.info(String.valueOf(file.exists()));
        String content = "Empty";
        try {
            content = new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            throw new Exception("Could not read template with ID : " + templateId);
        }
        logger.info(content);
        return content;
    }

    public String getTemplate(Map<String, String> replacements) {
        String cTemplate = this.template;

        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            cTemplate = cTemplate.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }
        return cTemplate;
    }
}