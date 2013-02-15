[#ftl]

[#assign content]

<h2>${msg['user.signup']!}</h2>

<form class="form-horizontal"
      action="/auth/login"
      method="post">

  <div class="control-group">
    <label class="control-label"
           for="inputEmail">${msg['user.email']}</label>
    <div class="controls">
      <input type="text"
             id="inputEmail"
             name="email"
             placeholder="email">
    </div>
  </div>

  <div class="control-group">
    <label class="control-label"
           for="inputPassword">${msg['user.password']}</label>
    <div class="controls">
      <input type="text"
             id="inputPassword"
             name="pass"
             placeholder="password">
    </div>
  </div>

  <div class="control-group">
    <div class="controls">
      <button type="submit"
              class="btn">${msg['user.button.send']}</button>
    </div>
  </div>

</form>

[/#assign]
[#include "../layout.ftl"/]