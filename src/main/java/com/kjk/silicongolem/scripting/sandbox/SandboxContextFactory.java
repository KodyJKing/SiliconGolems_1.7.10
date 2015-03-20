package com.kjk.silicongolem.scripting.sandbox;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;

import com.kjk.silicongolem.scripting.APIRegistry;

public class SandboxContextFactory extends ContextFactory {
	@Override
	protected Context makeContext() {
		Context cx = super.makeContext();
		cx.setClassShutter(new APIRegistry());
		cx.setWrapFactory(new SandboxWrapFactory());
		return cx;
	}
}
