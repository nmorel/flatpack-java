package com.getperka.flatpack.gwt.client;

import com.getperka.flatpack.gwt.Packer;
import com.getperka.flatpack.gwt.Unpacker;

public interface FlatPackApi extends Api
{
    /**
     * @return the packer
     */
    Packer getPacker();

    /**
     * @return the unpacker
     */
    Unpacker getUnpacker();
}
