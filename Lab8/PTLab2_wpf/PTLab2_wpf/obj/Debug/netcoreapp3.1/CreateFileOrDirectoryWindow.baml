   M S B A M L   `   `   `  ����E  APTLab2_wpf, Version=1.0.0.0, Culture=neutral, PublicKeyToken=null'clr-namespace:PTLab2_wpf
PTLab2_wpf  5   	   ,    &PTLab2_wpf.CreateFileOrDirectoryWindow   5   	   \ XPresentationFramework, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35^ ZWindowsFormsIntegration, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35e aSystem.Windows.Controls.Ribbon, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089W SPresentationCore, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35R NWindowsBase, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35H 9http://schemas.microsoft.com/winfx/2006/xaml/presentation      5   	   R NSystem.Xaml, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     5   	   @d2http://schemas.microsoft.com/expression/blend/2008     5   	   Jmc;http://schemas.openxmlformats.org/markup-compatibility/2006     5   	   *localclr-namespace:PTLab2_wpf     5   	     � Title$!  CreateFileOrDirectoryWindow��5   	   $	��245��6-   $	��274��6:   .��5	      � $	��292��5
   	   $	��372��6   $
��Left=�5   	   $	��Top=�6$   .+�5   
   �� $��10, 10q�5      $��50��5      $��20��6   $
��Left=�5      $	��Top=�6(   $��0q�5       �� 
IsReadOnly$
 True��5      .��6   Name:5      �� 5   
   -   5         ��Name$ NewItemName��5      $��65, 10q�5      $	��160��5      $��20��6   $��Right=�5      $	��Top=�6)   5      �� 5   
    �� Padding$ 10,0,0,0q�6   $��0,0,0,81q�6$   ._�5      �� $��0, 50q�5      $	��130��5      $��20��6   $
��Left=�5       $	��Top=�6,    �� Orientation$ 
Horizontal=�5!      .+�5#      	� -   5         	�Name$ FileRadioButton��5#      $��File ��67   	� 5$      -   5        $ DirectoryRadioButton��5$      $��	Directory��6<   5%      5&      �� 5(   
   $ 10,0,0,0q�6   $��
0,0,-6,122q�6$   ._�5)      �� $
��0,80q�5*      $��90��5+      $��80��6   $
��Left=�5,      $	��Top=�6,   .+�5.      �� -   5        $��ReadOnlyCheck��5.      .��60   
ReadOnly6:   �� 5/      -   5        $��ArchiveCheck��5/      .��6/   	Archive68   �� 50      -   5        $��HiddenCheck��50      .��6.   Hidden66   �� 51      -   5        $��SystemCheck��51      .��6.   System66   52      53      �� 55   
   -   5         ��Name$ SubmitButton��56      $��15,170q�57      $��65��58      $��25��6   $
��Left=�59      $	��Top=�6(    �� 	ClickMode$ Release=�5:   &   .��6;   Ok5=      �� 5?   
   -	   5        $ CancelButton��5@      $��100,170q�5A      $��70��5B      $��25��6   $
��Left=�5C      $	��Top=�6(   $ Release=�5D   &   .��6:   Cancel5G      5I      5J      