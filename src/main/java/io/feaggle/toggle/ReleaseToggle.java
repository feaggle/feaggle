package io.feaggle.toggle;

import io.feaggle.driver.DrivenBy;
import io.feaggle.toggle.release.ReleaseDriver;

import java.util.concurrent.atomic.AtomicBoolean;

public class ReleaseToggle implements DrivenBy<ReleaseDriver> {
    private final String name;
    private final AtomicBoolean enabled;

    public ReleaseToggle(String name, AtomicBoolean enabled) {
        this.name = name;
        this.enabled = enabled;
    }

    @Override
    public void drive(ReleaseDriver releaseDriver) {
        this.enabled.set(releaseDriver.isFlaggedForRelease(name));
    }

    @Override
    public String identifier() {
        return name;
    }

    public boolean isEnabled() {
        return this.enabled.get();
    }
}