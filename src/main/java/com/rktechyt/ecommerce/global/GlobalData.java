package com.rktechyt.ecommerce.global;

import java.util.*;
import com.rktechyt.ecommerce.model.*;

public class GlobalData {
    public static List<Product> cart;
    static {
        cart = new ArrayList<Product>();
    }
}
