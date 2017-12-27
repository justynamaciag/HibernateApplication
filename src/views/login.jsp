<div align="center">
    <h1>Order Application</h1>
</div>

<div style="float: right">
    <input style="background: none; border: none; text-decoration: underline" type="button" value="Log out" onclick="window.location='http://localhost:4567/logout'"><br><br>
</div>

<div style="float: right">
    <input style="background: none; border: none; text-decoration: underline" type="button" value="Orders" onclick="window.location='http://localhost:4567/orders'"><br><br>
</div>


<table width=100% align="center" display="inline">
    <tr>
        <td align="center">
            <fieldset>
            <legend><h2>Login</h2></legend>
                <div style="font-size: large; color: red;">
                    #if($authenticationFailed)
                        <p>BAD LOGIN/PASSWORD</p>
                    #elseif($authenticationSucceeded)
                        <p>Logged correctly</p>
                    #end
                </div>
            <form id="loginForm" method="get">
                <label>Username</label>
                <br>
                <input type="text" name="username" placeholder="Username" value="" required><br><br>
                <label>Password</label><br>
                <input type="password" name="password" placeholder="Password" value="" required><br><br>
                #if($loginRedirect)
                <input value="$loginRedirect">
                #end
                <br>
                <input type="submit" value="Login">
            </form>
        </fieldset>
        </td>
        <td align="center">
            <fieldset>
                <legend><h2>Register</h2></legend>
                #if($registerSuccess)
                    <p>New user added succesfully</p>
                #end
                <br>
                <form id="registerForm" method="get">
                    <label>Username</label><br>
                    <input type="text" name="usernameRegister" placeholder="Username" value="" required><br>
                    <label>Password</label><br>
                    <input type="password" name="passwordRegister" placeholder="Password" value="" required><br>
                    <label>Company Name</label><br>
                    <input type="text" name="companyname" placeholder="Company Name" value="" required><br>
                    <label>City</label><br>
                    <input type="text" name="city" placeholder="City" value="" required><br>
                    <label>Zip Code</label><br>
                    <input type="text" name="zipcode" placeholder="ZipCode" value="" required><br>
                    <label>Street</label><br>
                    <input type="text" name="street" placeholder="Street" value="" required><br>
                    <input name="registerInput" type="hidden">
                    <input type="submit" name="register" value="Register" onclick="{registerInput.value='done'}"/>
                </form>
            </fieldset>

        </td>
    </tr>
</table>

