package com.ribeiro.gabriel.easyfragmentmanager;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.Map;

/**
 * It is a easy FragmentManager
 * The EasyFragmentManager is a FragmentManager which manage the fragments on backStack and
 * keep control of them, giving to app a best performance and memory management
 *
 * @author Gabriel Ribeiro Silva \r\n email: gabriel.silva3409@gmail.com \r\n GitHub: ribGSilva
 */
public class EasyFragmentManager {

    /**
     * Activity that contain the manager
     */
    private Activity mActivity;
    /**
     * The id from layout where the layouts will be placed
     */
    private int mIdFromFrameToPutFragment;
    /**
     * Map of fragment classes
     */
    private Map<String, Class<?>> mFragmentTypesMap;
    /**
     * The current fragment type on focus
     */
    private String mCurrentFragmentType;
    /**
     * The current fragment instance on focus
     */
    private Fragment mCurrentFragment;
    /**
     * The android FragmentManager
     */
    private FragmentManager mSupportFragmentManager;

    /**
     * Private constructor that initiates the instance
     *
     * @param activity                 Activity that contain the manager
     * @param idFromFrameToPutFragment The id from layout where the layouts will be placed (EX: R.id.content_layout)
     * @param initialFragmentType      The initial fragment type
     * @param supportFragmentManager   The android FragmentManager
     */
    private EasyFragmentManager(final Activity activity,
                                int idFromFrameToPutFragment,
                                Map<String, Class<?>> fragmentTypesMap,
                                String initialFragmentType,
                                FragmentManager supportFragmentManager) {
        mActivity = activity;
        mIdFromFrameToPutFragment = idFromFrameToPutFragment;
        mFragmentTypesMap = fragmentTypesMap;
        mCurrentFragmentType = initialFragmentType;
        mSupportFragmentManager = supportFragmentManager;

        mCurrentFragment = createFragmentByType(mCurrentFragmentType);

        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.add(mIdFromFrameToPutFragment, mCurrentFragment, initialFragmentType);
        fragmentTransaction.addToBackStack(mCurrentFragmentType);
        fragmentTransaction.commit();
    }

    /**
     * Keep the manager updated about current fragment
     */
    public void updateManagerOnBackStackChange() {
        int lastItemOnStack = mSupportFragmentManager.getBackStackEntryCount() - 1;
        if (lastItemOnStack < 0) {
            mActivity.onBackPressed();
        } else {
            mCurrentFragmentType = mSupportFragmentManager.getBackStackEntryAt(lastItemOnStack).getName();
            mCurrentFragment = getFragmentFromBackStackByTag(mCurrentFragmentType);
        }
    }

    /**
     * Search for fragment on back stack
     *
     * @param fragmentToSearch The fragment type to search
     * @return Returns the Fragment instance on back stack or return null if there is not
     * instance of this type on the back stack
     */
    private Fragment getFragmentFromBackStackByTag(String fragmentToSearch) {
        return mSupportFragmentManager.findFragmentByTag(fragmentToSearch);
    }

    /**
     * Change the focused fragment by another
     *
     * @param newFragment    The next fragment to be focused
     * @param addToBackStack Add the previous fragment to back stack
     */
    public void changeFragment(String newFragment, boolean addToBackStack) {
        if (newFragment == mCurrentFragmentType) return;
        Fragment fragmentToAppear = getFragmentFromBackStackByTag(newFragment);
        if (fragmentToAppear == null) {
            fragmentToAppear = createFragmentByType(newFragment);
        }

        FragmentTransaction fragmentTransaction = mSupportFragmentManager.beginTransaction();
        fragmentTransaction.replace(mIdFromFrameToPutFragment, fragmentToAppear, newFragment);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(mCurrentFragmentType);
        }
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();

        mCurrentFragmentType = newFragment;
        mCurrentFragment = fragmentToAppear;
    }

    /**
     * Create a Fragment instance based on type on parameter
     *
     * @param newFragment Type of fragment to be created
     * @return Return a fragment instance of parameter type, or null if the class from enum is not a
     * fragment instance of if the constructor does not exist or the constructor is private
     */
    private Fragment createFragmentByType(String newFragment) {
        try {
            Class fragmentClass = mFragmentTypesMap.get(newFragment);
            Object fragment = fragmentClass.newInstance();
            return Fragment.class.cast(fragment);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Return the instance of the fragment on focus
     *
     * @return Instance of the fragment on focus
     */
    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }

    /**
     * Return the type of the fragment on focus
     *
     * @return Type of the fragment on focus
     */
    public String getCurrentFragmentType() {
        return mCurrentFragmentType;
    }

    /**
     * Factory method that create a instance of EasyFragmentManager
     *
     * @param activity                 Activity that contain the manager
     * @param idFromFrameToPutFragment The id from layout where the layouts will be placed (EX: R.id.content_layout)
     * @param initialFragmentType      The initial fragment type
     * @param supportFragmentManager   The android FragmentManager
     * @return Instance of EasyFragmentManager
     * @throws NullPointerException Throw exception if any parameter is null
     */
    public static EasyFragmentManager newInstance(Activity activity,
                                                  int idFromFrameToPutFragment,
                                                  Map<String, Class<?>> fragmentTypesMap,
                                                  String initialFragmentType,
                                                  FragmentManager supportFragmentManager)
            throws NullPointerException {
        if (activity == null)
            throw new NullPointerException("Activity null on parameter");
        if (fragmentTypesMap == null || fragmentTypesMap.isEmpty())
            throw new NullPointerException("Map null or empty on parameter");
        if (initialFragmentType == null)
            throw new NullPointerException("Fragment type null on parameter");
        if (supportFragmentManager == null)
            throw new NullPointerException("SupportFragmentManager null on parameter");

        return new EasyFragmentManager(
                activity, idFromFrameToPutFragment, fragmentTypesMap, initialFragmentType, supportFragmentManager);
    }
}
