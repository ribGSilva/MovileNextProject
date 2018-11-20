package com.ribeiro.gabriel.easyfragmentmanager;

/**
 * Method to change focused fragment implementable for host activity to control the EasyFragmentController
 *
 * @author Gabriel Ribeiro Silva \r\n email: gabriel.silva3409@gmail.com \r\n GitHub: ribGSilva
 */
public interface EasyFragmentController {
    void changeFragment(String newFragmentType, boolean addToBackStack);
}
