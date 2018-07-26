package org.launchcode.exercisemeetup.Models;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractEntity {

    @Id
    @GeneratedValue
    private int uid;

    public int getUid() {
        return this.uid;
    }
}
