/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.nativeplatform.platform.internal;

import org.gradle.internal.typeconversion.NotationParser;
import org.gradle.nativeplatform.platform.NativePlatform;
import org.gradle.platform.base.Platform;
import org.gradle.platform.base.PlatformContainer;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class DefaultNativePlatform implements NativePlatformInternal {
    private final NotationParser<Object, ArchitectureInternal> archParser;
    private final NotationParser<Object, OperatingSystemInternal> osParser;
    private final String name;
    private ArchitectureInternal architecture;
    private OperatingSystemInternal operatingSystem;

    public DefaultNativePlatform(String name, NotationParser<Object, ArchitectureInternal> archParser, NotationParser<Object, OperatingSystemInternal> osParser) {
        this.name = name;
        this.architecture = ArchitectureInternal.TOOL_CHAIN_DEFAULT;
        this.operatingSystem = DefaultOperatingSystem.TOOL_CHAIN_DEFAULT;
        this.archParser = archParser;
        this.osParser = osParser;
    }

    public DefaultNativePlatform(String name) {
        this(name, ArchitectureNotationParser.parser(), OperatingSystemNotationParser.parser());
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getDisplayName();
    }

    public String getDisplayName() {
        return String.format("platform '%s'", name);
    }

    public ArchitectureInternal getArchitecture() {
        return architecture;
    }

    public void architecture(Object notation) {
        architecture = archParser.parseNotation(notation);
    }

    public OperatingSystemInternal getOperatingSystem() {
        return operatingSystem;
    }

    public void operatingSystem(Object notation) {
        operatingSystem = osParser.parseNotation(notation);
    }

    public String getCompatibilityString() {
        return String.format("%s:%s", architecture.getName(), operatingSystem.getName());
    }

    public static Set<NativePlatform> getNativePlatforms(PlatformContainer allPlatforms) {
        Set<NativePlatform> platforms = new LinkedHashSet<NativePlatform>();
        for (Platform platform: allPlatforms) {
            if (platform instanceof NativePlatform) {
                platforms.add((NativePlatform) platform);
            }
        }
        return platforms;
    }
}
