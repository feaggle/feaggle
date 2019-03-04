package io.feaggle;

import io.feaggle.toggle.ExperimentToggle;
import io.feaggle.toggle.OperationalToggle;
import io.feaggle.toggle.ReleaseToggle;
import io.feaggle.toggle.experiment.ExperimentCohort;
import io.feaggle.toggle.experiment.ExperimentDriver;
import io.feaggle.toggle.operational.OperationalDriver;
import io.feaggle.toggle.release.ReleaseDriver;

import java.util.concurrent.atomic.AtomicBoolean;

public class Feaggle<Cohort extends ExperimentCohort> {
    private final ExperimentDriver<Cohort> experimentDriver;
    private final OperationalDriver operationalDriver;
    private final ReleaseDriver releaseDriver;

    private Feaggle(DriverLoader<Cohort> loader) {
        experimentDriver = loader.loadExperimentDriver();
        operationalDriver = loader.loadOperationalDriver();
        releaseDriver = loader.loadReleaseDriver();
    }

    public static <Cohort extends ExperimentCohort> Feaggle<Cohort> load(DriverLoader<Cohort> loader) {
        return new Feaggle<>(loader);
    }

    public ReleaseToggle release(String name) {
        ReleaseToggle toggle = new ReleaseToggle(name, new AtomicBoolean(false));
        toggle.drive(releaseDriver);
        return toggle;
    }

    public OperationalToggle operational(String name) {
        OperationalToggle toggle = new OperationalToggle(name, new AtomicBoolean(false));
        toggle.drive(operationalDriver);
        return toggle;
    }

    public ExperimentToggle<Cohort> experiment(String name) {
        ExperimentToggle<Cohort> toggle = new ExperimentToggle<>(name);
        toggle.drive(experimentDriver);
        return toggle;
    }
}
