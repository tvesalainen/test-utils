/*
 * Copyright (C) 2019 Timo Vesalainen <timo.vesalainen@iki.fi>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.vesalainen.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Timo Vesalainen <timo.vesalainen@iki.fi>
 */
public final class Asserts
{
    public static void assertSerializable(Serializable obj)
    {
        try
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try (ObjectOutputStream oas = new ObjectOutputStream(baos))
            {
                oas.writeObject(obj);
            }
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            try (ObjectInputStream ois = new ObjectInputStream(bais))
            {
                Object obj2 = ois.readObject();
                if (Objects.deepEquals(obj, obj2))
                {
                    throw new IllegalArgumentException(obj2+" deserialized is not equals to "+obj);
                }
            }
        }
        catch (IOException | ClassNotFoundException ex)
        {
            throw new IllegalArgumentException(ex);
        }
    }
}
