package ru.hogwarts.school.service;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Inform;
import ru.hogwarts.school.model.RunTimeBench;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.LongStream;

@Service
public class InfoService {

    @Value("${server.port:8080}")
    private String serverPort;

    private final Inform inform;

    public InfoService(Inform inform) {
        this.inform = inform;
    }

    private static final Logger logger = LoggerFactory.getLogger(InfoService.class);

    @PostConstruct
    public void setInfo() {
        this.inform.setPort(serverPort);
    }

    public String getInfoPort() {
        logger.info("was invoked method for app running port: {}", serverPort);
        return this.serverPort;
    }

    public List<RunTimeBench> getBench() {
        logger.info("Was invoked method for get run time benchmarks");
        List<RunTimeBench> benches = new LinkedList<>();
        RunTimeBench bench1 = new RunTimeBench();
        RunTimeBench bench2 = new RunTimeBench();

        //TEST 1: SERIAL
        String testName = "Тест вычисления в последовательном потоке в микросекундах";
        long startTime = System.nanoTime();
        long sum = LongStream
                .iterate(1, a -> a + 1)
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);
        long endTime = System.nanoTime();

        bench1.setTestType(testName);
        bench1.setLeadTime((endTime - startTime) / 1000);
        bench1.setCalculatedValue(sum);
        benches.add(bench1);

        //TEST 2: PARALLEL
        testName = "Тест вычисления в параллельном потоке в микросекундах";
        startTime = System.nanoTime();
        sum = LongStream
                .rangeClosed(1, 1_000_000)
                .parallel()
                .reduce(0, (a, b) -> a + b);
        endTime = System.nanoTime();

        bench2.setTestType(testName);
        bench2.setLeadTime((endTime - startTime) / 1000);
        bench2.setCalculatedValue(sum);
        benches.add(bench2);

        return benches;
    }

}
