package mhkif.yc.docguardian.config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class Seeders implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(Seeders.class);

    @Override
    public void run(String... args) throws Exception {

    }

}
