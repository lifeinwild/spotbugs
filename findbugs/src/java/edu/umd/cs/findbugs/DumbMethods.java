/*
 * FindBugs - Find bugs in Java programs
 * Copyright (C) 2003, University of Maryland
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package edu.umd.cs.findbugs;
import java.util.*;
import java.io.PrintStream;
import org.apache.bcel.classfile.*;
import java.util.zip.*;
import java.io.*;
import edu.umd.cs.pugh.visitclass.Constants2;

public class DumbMethods extends BytecodeScanningDetector implements   Constants2 {

   private HashSet<String> alreadyReported = new HashSet<String>();
   private BugReporter bugReporter;

   public DumbMethods(BugReporter bugReporter) {
	this.bugReporter = bugReporter;
   }

   public void sawOpcode(int seen) {
	if ((seen == INVOKESPECIAL)
				&& classConstant.equals("java/lang/String")
				&& nameConstant.equals("<init>")
				&& sigConstant.equals("(Ljava/lang/String;)V"))
		if (alreadyReported.add(betterMethodName))
			bugReporter.reportBug(new BugInstance("DM_STRING_CTOR", NORMAL_PRIORITY)
				.addClassAndMethod(this)
				.addSourceLine(this));
	if ((seen == INVOKESPECIAL)
				&& classConstant.equals("java/lang/String")
				&& nameConstant.equals("<init>")
				&& sigConstant.equals("()V"))
		if (alreadyReported.add(betterMethodName))
			bugReporter.reportBug(new BugInstance("DM_STRING_VOID_CTOR", NORMAL_PRIORITY)
				.addClassAndMethod(this)
				.addSourceLine(this));
	if (((seen == INVOKESTATIC
				&& classConstant.equals("java/lang/System"))
	    || (seen == INVOKEVIRTUAL
				&& classConstant.equals("java/lang/Runtime")))
				&& nameConstant.equals("gc")
				&& sigConstant.equals("()V")
				&& !betterClassName.startsWith("java.lang"))
		if (alreadyReported.add(betterMethodName))
			bugReporter.reportBug(new BugInstance("DM_GC", NORMAL_PRIORITY)
				.addClassAndMethod(this)
				.addSourceLine(this));
	if ((seen == INVOKESPECIAL)
				&& classConstant.equals("java/lang/Boolean")
				&& nameConstant.equals("<init>")
				&& !className.equals("java/lang/Boolean")
				)
		if (alreadyReported.add(betterMethodName))
			bugReporter.reportBug(new BugInstance("DM_BOOLEAN_CTOR", NORMAL_PRIORITY)
				.addClassAndMethod(this)
				.addSourceLine(this));
	}
}
