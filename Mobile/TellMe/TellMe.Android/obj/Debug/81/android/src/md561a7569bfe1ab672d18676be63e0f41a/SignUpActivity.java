package md561a7569bfe1ab672d18676be63e0f41a;


public class SignUpActivity
	extends android.app.Activity
	implements
		mono.android.IGCUserPeer
{
/** @hide */
	public static final String __md_methods;
	static {
		__md_methods = 
			"n_onCreate:(Landroid/os/Bundle;)V:GetOnCreate_Landroid_os_Bundle_Handler\n" +
			"";
		mono.android.Runtime.register ("TellMe.Droid.Activities.SignUpActivity, TellMe.Android", SignUpActivity.class, __md_methods);
	}


	public SignUpActivity ()
	{
		super ();
		if (getClass () == SignUpActivity.class)
			mono.android.TypeManager.Activate ("TellMe.Droid.Activities.SignUpActivity, TellMe.Android", "", this, new java.lang.Object[] {  });
	}


	public void onCreate (android.os.Bundle p0)
	{
		n_onCreate (p0);
	}

	private native void n_onCreate (android.os.Bundle p0);

	private java.util.ArrayList refList;
	public void monodroidAddReference (java.lang.Object obj)
	{
		if (refList == null)
			refList = new java.util.ArrayList ();
		refList.add (obj);
	}

	public void monodroidClearReferences ()
	{
		if (refList != null)
			refList.clear ();
	}
}
