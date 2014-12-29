/*
 * Copyright (C) 2014 Matej Kormuth <http://matejkormuth.eu>
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package ts3bot.helpers;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Helper for testing method signatures.
 */
public final class MethodSignatureTester {
    // Object methods.
    private static final List<MethodSignature> objectMethodSignatures = new ArrayList<MethodSignatureTester.MethodSignature>();
    
    static {
        for (Method m : Object.class.getDeclaredMethods()) {
            objectMethodSignatures.add(new MethodSignature(m.getName(),
                    m.getParameterTypes()));
        }
    }
    
    /**
     * Checks whether specified interface declares all of public methods implementation class declares.
     * 
     * @param impl
     *            implementation class
     * @param interf
     *            interface class
     * @throws RuntimeException
     *             When interface does not declare public method implementation class does
     */
    public static final void hasInterfAllImplPublicMethods(final Class<?> impl,
            final Class<?> interf) {
        List<MethodSignature> interfMethodSignatures = new ArrayList<MethodSignature>(
                100);
        
        // Generate interface method signatures.
        for (Method m : interf.getDeclaredMethods()) {
            // Interface has only public abstract methods.
            interfMethodSignatures.add(new MethodSignature(m.getName(),
                    m.getParameterTypes()));
        }
        
        for (Method m : impl.getDeclaredMethods()) {
            // Checking only public methods.
            MethodSignature ms;
            if (Modifier.isPublic(m.getModifiers())) {
                // Build method signature.
                ms = new MethodSignature(m.getName(), m.getParameterTypes());
                // Don't check methods derived from Object.
                if (!objectMethodSignatures.contains(ms)) {
                    // Check if interface declares it.
                    if (!interfMethodSignatures.contains(ms)) { throw new RuntimeException(
                            "Interface '" + interf.getName()
                                    + "' does not declare method " + ms.toString()
                                    + " implemented in class '" + impl.getName() + "'!"); }
                }
            }
        }
    }
    
    /**
     * Class that specified method signature in Java.
     */
    private static class MethodSignature {
        private final String     name;
        private final Class<?>[] params;
        
        public MethodSignature(final String name, final Class<?>[] params) {
            this.name = name;
            this.params = params;
        }
        
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
            result = prime * result
                    + ((this.params == null) ? 0 : this.params.hashCode());
            return result;
        }
        
        @Override
        public boolean equals(final Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (this.getClass() != obj.getClass())
                return false;
            MethodSignature other = (MethodSignature) obj;
            if (this.name == null) {
                if (other.name != null)
                    return false;
            }
            else if (!this.name.equals(other.name))
                return false;
            if (this.params == null) {
                if (other.params != null)
                    return false;
            }
            else if (this.params.length != other.params.length)
                return false;
            for (int i = 0; i < this.params.length; i++) {
                if (!this.params[i].equals(other.params[i])) { return false; }
            }
            return true;
        }
        
        @Override
        public String toString() {
            return "MethodSignature [name=" + this.name + ", params="
                    + Arrays.toString(this.params) + "]";
        }
    }
}
