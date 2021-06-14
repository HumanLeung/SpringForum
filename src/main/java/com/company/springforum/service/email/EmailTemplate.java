package com.company.springforum.service.email;

import org.springframework.stereotype.Component;

@Component
public class EmailTemplate implements Template{

    @Override
     public String loadTemplate(String username, Integer code){
        return (
                "<html>\n" +
                        "<head>\n" +
                        " \n" +
                        "</head>\n" +
                        "<body>\n" +
                        "\t<h1> Hi " +username+ " </h1>\n" +
                        "\t<br/>\n" +
                        "\t<h2> Your Code Number is "+ code +"</h2> \n" +
                        "\t<br/>\n" +
                        "\tThanks,\n" +
                        "</body>\n" +
                        "</html>"
                );
    }
}
