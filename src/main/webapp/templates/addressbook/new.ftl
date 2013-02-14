[#ftl]

[#assign errors = flash['errors']!/]

[#assign content]
  [#if errors??]
  <ol>
    [#list errors as e]
      <li>${e}</li>
    [/#list]
  </ol>
  [/#if]

<h2>${msg['user.add.addressbook']!}</h2>

<form class="form-horizontal"
      action="/contacts"
      method="post">

  <div class="control-group">
    <label class="control-label"
           for="inputTitle">${msg['user.title']!}
    </label>
    <div class="controls">
      <input type="text"
             id="inputTitle"
             name="title"
             placeholder=${msg['user.textbox.title']}>
    </div>
  </div>

  <div class="control-group">
    <lebal class="control-label"
           for="inputPhoneNumber">${msg['user.phonenumber']}</lebal>
    <div class="controls">
      <input type="text"
             id="inputPhoneNumber"
             name="phoneNumber"
             placeholder=${msg['user.textbox.phone.number']}>
    </div>
  </div>

  <div class="control-group">
    <label class="control-label"
           for="inputAddress">${msg['user.address']}</label>
    <div class="controls">
      <input type="textarea"
             rows="5"
             id="inputAddress"
             placeholder=${msg['user.textbox.address']}>
    </div>
  </div>

  <div class="control-group">
    <label class="control-label"
           for="inputComments">${msg['user.comments']}</label>
    <div class="controls">
      <input type="text"
             id="inputComments"
             name="comments"
             placeholder=${msg['user.textbox.comments']}>
    </div>
  </div>

  <div class="control-group">
    <label class="control-label"
           for="inputBirthDate">${msg['user.birth.date']}</label>
    <div class="controls">
      <input type="date"
             id="inputBirthDate"
             name="birthDate"
             value="2013-02-14"
             max="2045-01-01"
             min="1945-01-01">
      <input type="submit"
             value="отправить">
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
