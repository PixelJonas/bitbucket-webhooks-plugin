/*
 * The MIT License
 *
 * Copyright (c) 2016, CloudBees, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package nl.topicus.bitbucket.events;

import nl.topicus.bitbucket.model.repository.BitbucketServerRepository;
import nl.topicus.bitbucket.model.repository.BitbucketServerRepositoryOwner;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BitbucketPushEvent implements Event, Ignorable {
    private BitbucketServerRepositoryOwner actor;

    private BitbucketServerRepository repository;

    private BitbucketPushDetail push;

    public BitbucketServerRepositoryOwner getActor() {
        return actor;
    }

    public void setActor(BitbucketServerRepositoryOwner actor) {
        this.actor = actor;
    }

    public BitbucketServerRepository getRepository() {
        return repository;
    }

    public void setRepository(BitbucketServerRepository repository) {
        this.repository = repository;
    }

    public BitbucketPushDetail getPush() {
        return push;
    }

    public void setPush(BitbucketPushDetail push) {
        this.push = push;
    }

    @Override
    public Optional<String> getUsername() {
        return Optional.ofNullable(this.getActor() == null ? null : this.getActor().getUsername());
    }

    @Override
    public List<String> getBranches() {
        if (this.getPush() == null || this.getPush().getChanges() == null) {
            return new ArrayList<>();
        }
        return this.getPush().getChanges()
                .stream()
                .map(bitbucketPushChange -> bitbucketPushChange.getNew().getName())
                .collect(Collectors.toList());
    }
}
