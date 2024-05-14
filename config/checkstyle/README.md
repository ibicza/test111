### Summary
Эти файлы отвечают за настройку и внедрение CheckStyle в проект.

<ol>
<li>checkstyle.xml: Настройки для CheckStyle <li>
<li>suppressions.xml: Этот файл позволяет отключить некоторые правила <li>

<li>Чтобы забилдить проект несмотря на синтаксические ошибки нужно поменять значение
<code> <property name="severity" value="error"/> </code> на value="warning" в файле checkstyle.xml <li>

<li> Чтобы добавить CheckStyle в новый модуль нужно прописать id 'checkstyle' в plugins{} и добавить
<code>apply from: "${project.projectDir.parentFile}/config/checkstyle/checkstyle.gradle"</code><li>
</ol>
