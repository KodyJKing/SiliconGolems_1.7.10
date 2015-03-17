package com.kjk.silicongolem.scripting.sandbox;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;

import com.kjk.silicongolem.scripting.APIList;

public class SandboxContextFactory extends ContextFactory {
	@Override
	protected Context makeContext() {
		Context cx = super.makeContext();
		cx.setClassShutter(new APIList());
		cx.setWrapFactory(new SandboxWrapFactory());
		return cx;
	}
}
